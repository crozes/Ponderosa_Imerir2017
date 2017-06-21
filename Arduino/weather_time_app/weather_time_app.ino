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
        HOUR24
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


int btn_pin = 0; // lecture du signal bouton
int btn_pin_val  = 0;
int btn_pin_prev_val;
unsigned long in_game_hour_duration;
BUTTON_ST button_state;


// Machine à états bouton début


void btn_init(){
button_state=BTN_IDLE;
in_game_hour_duration=15000; //15 seccondes par heure par défaut
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
    if (btn_pin_prev_val > 50 && btn_pin_prev_val < 250 && btn_pin_val==0){
      next_button_state = BTN_PLS;
    }else{
     if (btn_pin_prev_val > 250 && btn_pin_prev_val < 450 && btn_pin_val==0){
        next_button_state = BTN_MNS;
      }
    }
    
    btn_pin_prev_val=  btn_pin_val;
    break;
    default:
    break;
   }

  button_state = next_button_state;
}
void btn_output(){
  switch(button_state){
    case BTN_PLS:
      in_game_hour_duration+=5000;//on ajoute 5 secondes par heure de jeu
    break;
    case BTN_MNS:
       in_game_hour_duration-=5000;//on enlève 5 secondes par heure de jeu
    break;
    case BTN_IDLE:
    break;
    default:
    break; 
  }
}

// Machine à états bouton fin

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
   Serial.begin(9600);
}

void loop(){
  Serial.write("coucou\n\0");
  delay(1000);
  btn_init();
  btn_update();
  btn_output();
}

