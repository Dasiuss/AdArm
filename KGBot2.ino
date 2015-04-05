#include <EEPROM.h>
#include <Stepper.h>

//Stepper S1= Stepper(48,0,3,2,1);  //w lewo kreci
//Stepper S1= Stepper(48,0,3,1,2);  //w prawo kreci

//Stepper S1 = Stepper(48,0,1,2,3);  // Y silnik  pierwotnie
//Stepper S2 = Stepper(48,0,3,1,2);  // X silnik  pierwotnie

Stepper S0 = Stepper(48,2,4,3,5);
Stepper S1 = Stepper(48,8,10,9,11);
Stepper S2 = Stepper(48,z1,z2,z3,z4);      //// piny do wpiny

 int posx=0;
 int posy=0;
 int posz=0;
 int xRange=100;						//maksymalne zasięgi ramienia
 int yRange=100;
 int zRange=100;
  int history[3][100];
  int lastHist=0;
  int redoPossib=0;
  int undoPossib=0;

int posxaddr=0 ;
int posyaddr=4 ;
int poszaddr=8 ;


void setup(){
  posx =  EEPROM.read(posxaddr);
  posy =  EEPROM.read(posyaddr);
  posz =  EEPROM.read(poszaddr);
  pinMode(6,OUTPUT);
  pinMode(7,OUTPUT);
digitalWrite(6,1);
digitalWrite(7,1);
S0 .setSpeed(400);
S1 .setSpeed(400);
S2 .setSpeed(400);
Serial.begin(9600);
}


void loop(){                                             //// Menu.
int chose;
int x,y,z;
do{
	chose = chOp();
	switch(chose){
	case(1):
		if(x>xRange || x<0 || y>yRange || y<0 || z>zRange || z<0){       //trzeba dodać jakoś x/y/z imo można w jakiejś osobnej funkcji
			x=posx;
			y=posy;
			z=posz;
			Serial.println("!!Wyjscie poza zakres!!");
		}
		moveTo(x,y,z);
	break;

	case(2):
		undo();
	break;

	case(3):
		redo();
	break;

		savepos();
	}
								
}while(chose<0 || chose>3);

delay(1000);

}


int chOp(){
	int number=0;
	
	Serial.println("1-sterowanie x/y/z  2-cofnij  3-cofnij cofniecie");
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
      default:
        Serial.println("Blad");
        break;
    }


	return number;												////// do wyboru sposób wybierania (przez stany na pinach?)
}




void move(float x , float y, float z){
int max;
int stepx = abs(x)+0.5;
int stepy = abs(y)+0.5;
int stepz = abs(z)+0.5;
 int dx=1;
    if(x<0) dx=-1;
 int dy=1;
    if(y<0) dy=-1;
 int dz=1;
	if(z<0) dy=-1;

	if(stepx>stepy && stepx>stepz) max=stepx;
	if(stepy>stepx && stepy>stepz) max=stepy;
	if(stepz>stepx && stepz>stepy) max=stepz;

for(int round=0; round<48; round++){
  for(int i = 1 ; i<(stepx*stepy*stepz)+1 ; i++) {
	  for( int j=1 ; j<max+1 ; j++){
    if(((((stepx*stepy*stepz)/stepx)*j)/i)==1) S0.step(dx);
	if(((((stepx*stepy*stepz)/stepy)*j)/i)==1) S1.step(dy);
    if(((((stepx*stepy*stepz)/stepz)*j)/i)==1) S2.step(dz);
	  }
  }
}
  posx+=x*48;
  posy+=y*48;
  posz+=z*48;

  saveHist();
}

void moveTo(float x, float y, float z){
 move(posx/48 - x, posy/48 - y , posz/48 - z);
}

void up(float y){
 move( 0 , y);
}

void upTo(float y){
 up( y-posy);
}

void down(float y){
  move(0 , -y);
}

void downTo(float y){//same as upTo
 up( y-posy);
}

void saveHist(){
  if(undoPossib<99) undoPossib++;
 history[0][lastHist] = posx;
 history[1][lastHist] = posy;
 history[2][lastHist] = posz;
  lastHist= (lastHist+1)%100;
  redoPossib=0;
}

void undo(){
  if(undoPossib>0){
   lastHist= (lastHist-1)%100;
  redoPossib++;
 moveTo(history[0][lastHist] , history[1][lastHist] , history[2][lastHist]);
  undoPossib--;
  }
}

void redo(){
  if(redoPossib>0){
 lastHist++;
  redoPossib--;
 moveTo(history[0][lastHist] , history[1][lastHist] , history[2][lastHist]);
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
