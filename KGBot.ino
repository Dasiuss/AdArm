#include <EEPROM.h>
#include <Stepper.h>

//Stepper S1= Stepper(48,0,3,2,1);  //w lewo kreci
//Stepper S1= Stepper(48,0,3,1,2);  //w prawo kreci

//Stepper S1 = Stepper(48,0,1,2,3);  // Y silnik  pierwotnie
//Stepper S2 = Stepper(48,0,3,1,2);  // X silnik  pierwotnie

Stepper S0 = Stepper(48,0,1,2,3);
Stepper S1 = Stepper(48,4,5,6,7);
Stepper S2 = Stepper(48,8,9,10,11);      //// piny do wpiny

 int posx=0;
 int posy=0;
 int posz=0;
 int xRange=100;						//maksymalne zasiêgi ramienia
 int yRange=100;
 int zRange=100;
  int history[3][100];
  int lastHist=0;
  int redoPossib=0;
  int undoPossib=0;
  bool historyBrowsing=false;

int posxaddr=0 ;
int posyaddr=4 ;
int poszaddr=8 ;


void setup(){
  posx = 0;  // EEPROM.read(posxaddr);
  posy = 0;  //EEPROM.read(posyaddr);
  posz = 0; // EEPROM.read(poszaddr);
  pinMode(12,OUTPUT);
  pinMode(13,OUTPUT);
 S0 .setSpeed(400);
S1 .setSpeed(400);
S2 .setSpeed(400);
Serial.begin(9600);
}


void loop(){                                             //// Menu.
int x,y,z;
int chose = chOp();

do{
	switch(chose){
	
	if(chose==1 || chose==2 || chose==3){
		if(chose==1)x=Serial.read();
		if(chose!=3)y=Serial.read();
		if(chose!=2)z=Serial.read();
	}

	case(1):
		moveTo(x,y,z);
	break;

	case(2):
		hight(z);
	break;

	case(3):
		orient(y);
	break;
	
	case(4):
		undo();
	break;
	
	case(5):
		redo();
	break;
		}								
}while(chose<0 || chose>3);

delay(1000);

}


int chOp(){
	int number=0;

	switch (Serial.read()){
      case '1':
        number=1;
        break;
      case '2':
        number=2;
        break;
      case '3':
        number=3;
		break;
	  case '4':
        number=4;
        break;
      case '5':
        number=5;
        break;      
      default:
		  break;
	return number;
}

/*


1. Wyznaczamy stosunek kroków poszczególnych silników np 1 do 1.5 do 5 (dla np liczb kroków 4, 6, 20).
2. W pêtli mno¿yæ i razy stosunek np k(i) = i*ratio1
3. Wykonaæ floor(k(i)) - floor(k(i-1)) kroków (czyli tyle, ile pe³nych liczb ca³kowitych minê³o)

Optymalizacja : k(i) =k(i-1)+ratio1
Wyznaczaæ stosunek, tak, ¿eby najwiêksza iloœæ kroków mia³a 1. Np 0.2 do 0.25 do 1 (przyk³adem z pkt 1)

Co myœlicie?


*/

void move(float x , float y, float z){

int stepx = (abs(x)+0.5)*48;
int stepy = (abs(y)+0.5)*48;
int stepz = (abs(z)+0.5)*48;

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
saveHist();
}

void moveTo(float x, float y, float z){
 move(posx/48 - x, posy/48 - y , posz/48 - z);
}

void hight(float z){
 move( 0 , 0 , z);
}

void hightTo(float z){
 hight( z-posz);
}

void orient(float y){
 move( 0 , y , 0);
}

void orientTo(float y){
 orient( y-posy);
}



void saveHist(){
  if(undoPossib<99) undoPossib++;
 history[0][lastHist] = posx;
 history[1][lastHist] = posy;
 history[2][lastHist] = posz;
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
  int x = posx;
 EEPROM.write(posxaddr, x%256);
 x>>=8;
 EEPROM.write(posxaddr+1, x%256);

  int y = posy;
 EEPROM.write(posyaddr, y%256);
 y>>=8;
 EEPROM.write(posyaddr+1, y%256);

  int z = posz;
 EEPROM.write(poszaddr, z%256);
 z>>=8;
 EEPROM.write(poszaddr+1, z%256);
}

void loadpos(){
 posx = EEPROM.read(posxaddr+1);
posx<<=8;
  posx +=EEPROM.read(posxaddr);

  posy = EEPROM.read(posyaddr+1);
posy<<=8;
  posy +=EEPROM.read(posyaddr);

   posz = EEPROM.read(poszaddr+1);
posz<<=8;
  posz +=EEPROM.read(poszaddr);
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