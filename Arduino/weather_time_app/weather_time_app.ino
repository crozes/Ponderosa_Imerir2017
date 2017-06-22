#include <LiquidCrystal.h>


typedef enum {
	SUNNY,
	HEAT_WAVE,
	STORM,
	RAIN,
	CLOUD
} WEATHER_ST;

typedef enum {
	SUNNY_F,
	HEAT_WAVE_F,
	STORM_F,
	RAIN_F,
	CLOUD_F
} FORECAST_ST;

typedef enum {
	IDLE_DISP,
	DISP
} DISPLAY_ST;

typedef enum {
	WAIT,
	TIME_PP,
	HOUR_12,
	HOUR_24
} CLOCK_ST;

typedef enum {
	BTN_IDLE,
	BTN_PLS,
	BTN_MNS
} BUTTON_ST;

typedef enum {
	WAIT_SR,
	SEND_SR
} SERIAL_ST;

//variables globales start
//variables globales end

//CLOCK globales start
int day_count;
int hour_count;
unsigned long in_game_hour_duration,previous_time;
CLOCK_ST clock_state;
//CLOCK globales end

//DISPLAY variables Start
LiquidCrystal lcd(8, 9, 4, 5, 6, 7);		 // select the pins used on the LCD panel
DISPLAY_ST display_state;
unsigned long time, delay_refresh;
//DISPLAY variables End

//BUTTON variables Start
int btn_pin = 0; // lecture du signal bouton
int btn_pin_val = 0;
int btn_pin_prev_val;
BUTTON_ST button_state;
//BUTTON variables End

//SERIAL variables start
SERIAL_ST serial_state;
char tampon[128];
//SERIAL variables end

// Machine à états CLOCK début

void clock_init(){
	day_count=0;
	hour_count=0;
	clock_state=WAIT;
}
void clock_update(){
	CLOCK_ST next_clock_state = clock_state;

	switch(clock_state){
		case WAIT:
			if(millis()-previous_time>in_game_hour_duration){
				next_clock_state=TIME_PP;
				previous_time=millis();
			}
		break;
		case TIME_PP:
			if(hour_count==12){
				next_clock_state = HOUR_12;
			}else{
				if(hour_count==24){
				next_clock_state = HOUR_24;
				}else{
					next_clock_state = WAIT;
				}
			}
		break;
		case HOUR_12:
			next_clock_state = WAIT;
		break;
		case HOUR_24:
			next_clock_state = WAIT;
		break;
		default:
		break; 
	}


	clock_state = next_clock_state;
}
void clock_output(){
	switch(clock_state){
		case WAIT:
		break;
		case TIME_PP:
		hour_count++;
		break;
		case HOUR_12:
		break;
		case HOUR_24:
		day_count++;
		hour_count=0;
		break;
		default:
		break; 
	}
}

// Machine à états CLOCK fin

// Machine à états SERIAL début

void serial_init(){
	serial_state = WAIT_SR;
	Serial.begin(9600);
}
void serial_update(){
	SERIAL_ST next_serial_state = serial_state;

	switch(serial_state){
		case WAIT_SR:
			if(clock_state==TIME_PP || clock_state==HOUR_12 || clock_state==HOUR_24){
				next_serial_state=SEND_SR;
			}
		break;
		case SEND_SR:
			next_serial_state=WAIT_SR;
		break;
		default:
		break;
	}


	serial_state = next_serial_state;
}
void serial_output(){
	switch (serial_state){
		case WAIT_SR:
		break;
		case SEND_SR:
			switch(clock_state){
			case HOUR_24:
			case HOUR_12:
			//météo generator
			case TIME_PP:
				sprintf(tampon,"{'time' : '%u'}\0",hour_count+day_count*24);
				Serial.write(tampon);
			break;
			default:
			break;
			}
		break;
		default:
		break;
	}
}

// Machine à états SERIAL fin

// Machine à états DISPLAY début

void display_init(){
	display_state=IDLE_DISP;
	delay_refresh=60;		//60 millisecondes avant rafraichissement
	time=millis();
	lcd.begin(16, 2);		// start the library
}
void display_update(){
	DISPLAY_ST next_display_state = display_state;

	switch(display_state){
		case IDLE_DISP:
			if(millis()-time>delay_refresh){
				next_display_state=DISP;
				time=millis();
			}
		break;
		case DISP:
		next_display_state=IDLE_DISP;
		break;
		default:
		break;
	}


	display_state = next_display_state;
}
void display_output(){
	switch(display_state){
		case IDLE_DISP:
		break;
		case DISP:
			lcd.setCursor(0,0);		// set the LCD cursor	 position 
			lcd.print("Day:");		// print a simple message on the LCD
			
			lcd.setCursor(4,0);
			lcd.print(day_count);
		
			lcd.setCursor(7,0);
			lcd.print("Hours:");
			
			lcd.setCursor(13,0);		//clean before refresh
			lcd.print("  ");
			
			lcd.setCursor(13,0);
			lcd.print(hour_count);
			
			lcd.setCursor(0,1);		// set the LCD cursor	 position 
			lcd.print("1Hour:");		// print a simple message on the LCD
			
			lcd.setCursor(7,1);
			lcd.print("      ");
			
			lcd.setCursor(7,1);
			lcd.print(in_game_hour_duration);
			
			lcd.setCursor(14,1);
			lcd.print("ms");
		break;
		default:
		break;
	}
}
// Machine à états DISPLAY fin


// Machine à états BUTTON début


void btn_init(){
button_state=BTN_IDLE;
in_game_hour_duration=16384; //14 seccondes par heure par défaut
btn_pin_prev_val = btn_pin_val;
}

void btn_update(){
	BUTTON_ST next_button_state = button_state;
	
	btn_pin_val= analogRead(btn_pin); 
	
	
	 switch(button_state){
		case BTN_PLS:
				next_button_state = BTN_IDLE;
		break;
		case BTN_MNS:
				next_button_state = BTN_IDLE;
		break;
		case BTN_IDLE:
		if (in_game_hour_duration < 120000 && btn_pin_prev_val > 50 && btn_pin_prev_val < 250 && (btn_pin_val < 50 || btn_pin_val > 250)){
			next_button_state = BTN_PLS;
		}else{
		 if (in_game_hour_duration > 500 && btn_pin_prev_val > 250 && btn_pin_prev_val < 450 && (btn_pin_val < 250 || btn_pin_val > 450)){
				next_button_state = BTN_MNS;
			}
		}
		
		btn_pin_prev_val=	btn_pin_val;
		break;
		default:
		break;
	 }

	button_state = next_button_state;
}
void btn_output(){
	switch(button_state){
		case BTN_PLS:
			in_game_hour_duration*=2;		//on ajoute 2 secondes par heure de jeu
		break;
		case BTN_MNS:
			 in_game_hour_duration/=2;		//on enlève 2 secondes par heure de jeu
		break;
		case BTN_IDLE:
		break;
		default:
		break; 
	}
}

// Machine à états BUTTON fin

/* toute machine a état dispose de 3 méthodes
void X_init(){}
void X_update(){
	X_ST next_X_state = X_state;

	switch(X_state){
		case:
		break;
		default:
		break; 
	}


	X_state = next_X_state;
}
void X_output(){
	switch(X_state){
		case:
		break;
		default:
		break; 
	}
}

*/

void setup(){
	serial_init();
	btn_init();
	clock_init();
	display_init();
}

void loop(){

	clock_update();
	clock_output();
	
	btn_update();
	btn_output();
	
	display_update();
	display_output();
	
	serial_update();
	serial_output();
}

