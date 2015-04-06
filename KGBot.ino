#include <EEPROM.h>
#include <Stepper.h>

Stepper S0 = Stepper(48,0,1,2,3);
Stepper S1 = Stepper(48,4,5,6,7);
Stepper S2 = Stepper(48,8,9,10,11);      //// piny do wpiny

int posX=0;
int posY=0;
int posZ=0;
int xRange=100;						//maksymalne zasiêgi ramienia
int yRange=100;
int zRange=100;
int history[3][100];
int lastHist=0;
int redoPossib=0;
int undoPossib=0;
bool historyBrowsing=false;

int posXaddr=0 ;
int posYaddr=4 ;
int posZaddr=8 ;


void setup(){
	posX = 0;  // EEPROM.read(posXaddr);
	posY = 0;  //EEPROM.read(posYaddr);
	posZ = 0; // EEPROM.read(posZaddr);
	pinMode(12,OUTPUT);
	pinMode(13,OUTPUT);
	S0 .setSpeed(400);
	S1 .setSpeed(400);
	S2 .setSpeed(400);
	Serial.begin(9600);
}


void loop(){                                             //// Menu.
	int x,y,z;

	int chose = Serial.read();

	switch(chose){
		case(1):
			x=Serial.read();
			y=Serial.read();
			z=Serial.read();

			move(x,y,z);
		break;

		case(2):
			x=Serial.read();
			y=Serial.read();
			z=Serial.read();

			moveTo(x,y,z);
		break;

		case(3):
			y=Serial.read();
			sides(y);
		break;
		case(4):
			y=Serial.read();
			sidesTo(y);
		break;

		case(5):
			z=Serial.read();
			vertical(z);
		break;
		case(6):
			z=Serial.read();
			verticalTo(z);
		break;

		case(7):
			undo();
		break;

		case(8):
			redo();
		break;
	}
	delay(1000);
}


void move(int x , int y, int z){

	int stepx = abs(x)+0.5;
	int stepy = abs(y)+0.5;
	int stepz = abs(z)+0.5;

	int maxStep;
	maxStep = max(stepx,max(stepy,stepz));

	float ratioX =stepx/maxStep;
	float ratioY =stepy/maxStep;
	float ratioZ =stepz/maxStep;

	int dx=x<0?-1:1;
	int dy=y<0?-1:1;
	int dz=z<0?-1:1;

	float kx=0,ky=0,kz=0;
	for (int i = 0 ; i < maxStep ; i++){
		kx+=ratioX;
		if(kx>=1){
			kx--;
			S0.step(dx);
		}
		ky+=ratioY;
		if(ky>=1){
			ky--;
			S1.step(dy);
		}
		kz+=ratioZ;
		if(kz>=1){
			kz--;
			S2.step(dz);
		}
	}
	posX+=x;
	posY+=y;
	posZ+=z;
	saveHist();
}

void moveTo(float x, float y, float z){
 move(posX/48 - x, posY/48 - y , posZ/48 - z);
}

void vertical(float z){
 move( 0 , 0 , z);
}

void verticalTo(float z){
 vertical( z-posZ);
}

void sides(float y){
 move( 0 , y , 0);
}

void sidesTo(float y){
 sides( y-posY);
}



void saveHist(){
  if(undoPossib<99) undoPossib++;
 history[0][lastHist] = posX;
 history[1][lastHist] = posY;
 history[2][lastHist] = posZ;
  lastHist= (lastHist+1)%100;
  if(historyBrowsing==false)redoPossib=0;
}

void undo(){
  if(undoPossib>0){
   lastHist= (lastHist-1)%100;
  redoPossib++;
historyBrowsing=true;
 moveTo(history[0][lastHist] , history[1][lastHist] , history[2][lastHist]);
historyBrowsing=false;
 undoPossib-=2;
  }
}

void redo(){
  if(redoPossib>0){
 lastHist++;
redoPossib--;
historyBrowsing=true;
  moveTo(history[0][lastHist] , history[1][lastHist] , history[2][lastHist]);
historyBrowsing=false;
  undoPossib++;
  }
}


/*

void savepos(){
  int x = posX;
 EEPROM.write(posXaddr, x%256);
 x>>=8;
 EEPROM.write(posXaddr+1, x%256);

  int y = posY;
 EEPROM.write(posYaddr, y%256);
 y>>=8;
 EEPROM.write(posYaddr+1, y%256);

  int z = posZ;
 EEPROM.write(posZaddr, z%256);
 z>>=8;
 EEPROM.write(posZaddr+1, z%256);
}

void loadpos(){
 posX = EEPROM.read(posXaddr+1);
posX<<=8;
  posX +=EEPROM.read(posXaddr);

  posY = EEPROM.read(posYaddr+1);
posY<<=8;
  posY +=EEPROM.read(posYaddr);

   posZ = EEPROM.read(posZaddr+1);
posZ<<=8;
  posZ +=EEPROM.read(posZaddr);
}
*/

/* UNABLED:

w loopie:

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