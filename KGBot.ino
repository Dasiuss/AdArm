#include <EEPROM.h>

#include <Stepper.h>

//Stepper S1= Stepper(48,0,3,2,1);  //w lewo kreci
//Stepper S1= Stepper(48,0,3,1,2);  //w prawo kreci

//Stepper S1 = Stepper(48,0,1,2,3);  // Y silnik  pierwotnie
//Stepper S2 = Stepper(48,0,3,1,2);  // X silnik  pierwotnie


Stepper S0 = Stepper(48,2,4,3,5);
Stepper S1 = Stepper(48,8,10,9,11);

 int posx=0;
 int posy=0;
  int history[2][100];
  int lastHist=0;
  int redoPossib=0;
  int undoPossib=0;
  
int posxaddr=0 ;
int posyaddr=4 ; 

void setup(){
  posx=  EEPROM.read(posxaddr);
  posy=  EEPROM.read(posyaddr); 
  pinMode(6,OUTPUT);
  pinMode(7,OUTPUT);
digitalWrite(6,1);
digitalWrite(7,1);
S0 .setSpeed(400);
S1 .setSpeed(400);
//Serial.begin(9600);
}




void loop(){
 
 move(10,20);
 
  
/*for ( int i = 0 ; i<480 ; i++){
  S0 .setSpeed(120+i);
  S0.step(1);
}
  S0 .setSpeed(600);
  S0.step(5*96);

for ( int i = 0 ;i<480;i++){
  S0 .setSpeed(600-i);
  S0.step(1);
}
*/
delay(1000);

}

void move(float x , float y){
 
int stepx = abs(x)+0.5;
int stepy = abs(y)+0.5;
 int dx=1;
    if(x<0) dx=-1; 
 int dy=1;
    if(y<0) dy=-1; 
  
  for(int i = 0 ; i<stepx*stepy*48 ; i++) {
   if(i%stepx == 0 ) S1.step(dy);
   if(i%stepy == 0 ) S0.step(dx);     
  } 
  posx+=x*48;
  posy+=y*48;
  
  saveHist();
}

void moveTo(float x, float y){
  
 move(posx/48 - x, posy/48 - y);
  
}


void up(float y){
 move( 0 , y);
}

void down(float y){
  move(0 , -y);
}

void saveHist(){
  if(undoPossib<99) undoPossib++;
 history[0][lastHist] = posx;
 history[1][lastHist] = posy;
  lastHist= (lastHist+1)%100;
  redoPossib=0;
}

void undo(){
  if(undoPossib>0){
   lastHist= (lastHist-1)%100;
  redoPossib++;
 moveTo(history[0][lastHist] , history[1][lastHist]);
  undoPossib--;
  } 
}

void redo(){
  if(redoPossib>0){
 lastHist++;
  redoPossib--; 
 moveTo(history[0][lastHist] , history[1][lastHist]);
 undoPossib++;
  }
}


void savepos(){
  int x = posx;
 EEPROM.write(posxaddr, x%256);
 x>>=8;
 EEPROM.write(posxaddr+1, x%256);
 
  int y = posy;
 EEPROM.write(posyaddr, y%256);
 y>>=8;
 EEPROM.write(posyaddr+1, y%256);

}

void loadpos(){
 posx = EEPROM.read(posxaddr+1);
posx<<=8; 
  posx +=EEPROM.read(posxaddr);

  posy = EEPROM.read(posyaddr+1);
posy<<=8; 
  posy +=EEPROM.read(posyaddr);
}
