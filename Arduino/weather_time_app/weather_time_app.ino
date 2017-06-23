#include <LiquidCrystal.h>
#define NB_PREVISIONS 10

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

//FORECAST variables start
FORECAST_ST forecast_state[NB_PREVISIONS+1];
char forecast_buffer[NB_PREVISIONS][32];
//FORECAST variables end

// Machine à états WEATHER début

void forecast_init(){
	int next_weather;

	for(int i=0;i<NB_PREVISIONS;i++){
		next_weather= random(100);
		if(next_weather<15){
			forecast_state[i]=RAIN_F;
		}else{
			if(next_weather<35){
				forecast_state[i]=CLOUD_F;
			}
			else{
				if(next_weather<75){
					forecast_state[i]=SUNNY_F;
				}else{
					if(next_weather<95){
						forecast_state[i]=HEAT_WAVE_F;
					}else{
						forecast_state[i]=STORM_F;
					}
				}
			}
		}
	}
}
void weather_update(){
	if(clock_state==HOUR_12 || clock_state==HOUR_24){
		int next_weather;
		FORECAST_ST next_forecast_state[NB_PREVISIONS];
		for(int i=0;i<NB_PREVISIONS;i++){
		next_forecast_state[i] = forecast_state[i];
			switch(forecast_state[i]){
				case SUNNY_F:
				case CLOUD_F:
				case RAIN_F:
				case STORM_F:
				case HEAT_WAVE_F:
					if(i==NB_PREVISIONS-1){			//si on est au bout des prévisions, on fait un random
						next_weather= random(100);
						if(next_weather<14){
							next_forecast_state[i]=RAIN_F;
						}else{
							if(next_weather<34){
								next_forecast_state[i]=CLOUD_F;
							}
							else{
								if(next_weather<74){
									next_forecast_state[i]=SUNNY_F;
								}else{
									if(next_weather<94){
										next_forecast_state[i]=HEAT_WAVE_F;
									}else{
										next_forecast_state[i]=STORM_F;
									}
								}
							}
						}
					}else{					//sinon, on regarde à la demi-journée suivante et on confirme
						switch(forecast_state[i+1]){
							case SUNNY_F:
								next_weather= random(100);
								if(next_weather<87){
									next_forecast_state[i]=SUNNY_F;
								}else{
									if(next_weather<92){
										next_forecast_state[i]=CLOUD_F;
									}
									else{
										if(next_weather<97){
											next_forecast_state[i]=HEAT_WAVE_F;
										}
										else{
											if(next_weather<98){
												next_forecast_state[i]=STORM_F;
											}
											else{
												if(next_weather<99){
													next_forecast_state[i]=RAIN_F;
												}
											}
										}
									}
								}
							break;
							case HEAT_WAVE_F:
								next_weather= random(100);
								if(next_weather<87){
									next_forecast_state[i]=HEAT_WAVE_F;
								}else{
									if(next_weather<92){
										next_forecast_state[i]=SUNNY_F;
									}
									else{
										if(next_weather<97){
											next_forecast_state[i]=STORM_F;
										}
										else{
											if(next_weather<98){
												next_forecast_state[i]=CLOUD_F;
											}
											else{
												if(next_weather<99){
													next_forecast_state[i]=RAIN_F;
												}
											}
										}
									}
								}
							break;
							case STORM_F:
								next_weather= random(100);
								if(next_weather<87){
									next_forecast_state[i]=STORM_F;
								}else{
									if(next_weather<92){
										next_forecast_state[i]=HEAT_WAVE_F;
									}
									else{
										if(next_weather<97){
											next_forecast_state[i]=RAIN_F;
										}
										else{
											if(next_weather<98){
												next_forecast_state[i]=CLOUD_F;
											}
											else{
												if(next_weather<99){
													next_forecast_state[i]=SUNNY_F;
												}
											}
										}
									}
								}
							break;
							case RAIN_F:
								next_weather= random(100);
								if(next_weather<87){
									next_forecast_state[i]=RAIN_F;
								}else{
									if(next_weather<92){
										next_forecast_state[i]=STORM_F;
									}
									else{
										if(next_weather<97){
											next_forecast_state[i]=CLOUD_F;
										}
										else{
											if(next_weather<98){
												next_forecast_state[i]=HEAT_WAVE_F;
											}
											else{
												if(next_weather<99){
													next_forecast_state[i]=SUNNY_F;
												}
											}
										}
									}
								}
							break;
							case CLOUD_F:
								next_weather= random(100);
								if(next_weather<87){
									next_forecast_state[i]=CLOUD_F;
								}else{
									if(next_weather<92){
										next_forecast_state[i]=RAIN_F;
									}
									else{
										if(next_weather<97){
											next_forecast_state[i]=SUNNY_F;
										}
										else{
											if(next_weather<98){
												next_forecast_state[i]=HEAT_WAVE_F;
											}
											else{
												if(next_weather<99){
													next_forecast_state[i]=STORM_F;
												}
											}
										}
									}
								}
							break;
							default:
							break;
						}
					}
				break;
				default:
				//ne reien faire
				break;
			}
			forecast_state[i] = next_forecast_state[i];
		}
	}
}

void weather_output(){
	for(int i = 0;i<NB_PREVISIONS;i++){
		switch(forecast_state[i]){
			case SUNNY_F:
				sprintf(forecast_buffer[i],"%s","sunny");
			break;
			case HEAT_WAVE_F:
				sprintf(forecast_buffer[i],"%s","heatwave");
			break;
			case STORM_F:
				sprintf(forecast_buffer[i],"%s","thunderstorm");
			break;
			case RAIN_F:
				sprintf(forecast_buffer[i],"%s","rainny");
			break;
			case CLOUD_F:
				sprintf(forecast_buffer[i],"%s","cloudy");
			break;
			default:
			break;
		}
	}
}

// Machine à états WEATHER fin


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
			break;
			case HOUR_12:
			break;
			//météo generator
			case TIME_PP:
				//sprintf(tampon,"{\"time\":\"%u\"}\n\0",hour_count+day_count*24);
				sprintf(tampon,"time%u",hour_count+day_count*24);
				for(int i= 0;i<NB_PREVISIONS;i++){
					strcat(tampon,",");
					strcat(tampon,forecast_buffer[i]);
				}
				strcat(tampon,"\n\0");
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
			
			lcd.setCursor(14,0);		//clean before refresh
			lcd.print("  ");
			
			lcd.setCursor(14,0);
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
	forecast_init();
	display_init();
}

void loop(){

	clock_update();
	clock_output();
	
	btn_update();
	btn_output();
	
	display_update();
	display_output();
	
	weather_update();
	weather_output();
	
	serial_update();
	serial_output();
}

