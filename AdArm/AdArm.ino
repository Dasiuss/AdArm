#include <Servo.h>


//enums
int _system = 20, laser = 21, C0 = 0, C1 = 1, C2 = 2, S0 = 10, S1 = 11, S2 = 12;
int _delay = 0, _move = 1, _moveFast = 2,
    _attach = 3, _detach = 4,
    _setIntensity = 5, _setStepDelay = 6;

Servo C[3];
int lastPos[3];
int stepDelay = 2;

int laserPin = 6;

int mapC[3][2] =
{
  {20, 125},
  {6, 180},
  {180, -10}
};


void setup() {
  Serial.begin(9600);

  pinMode(laserPin, OUTPUT);

  Serial.read();
  Serial.print("rdy");

}

void loop() {
  int element, command, values[4];

  do {
    element = Serial.read();
  } while (element == -1);

  do {
    command = Serial.read();
  } while (command == -1);

  for (int i = 0 ; i < 4 ; i++) {
    do {
      values[i] = Serial.read();
    } while (values[i] == -1);
  }

  Serial.print("ack");

  int value = 0;
  for (int i = 0 ; i < 4 ; i++) {
    value = value << 8;
    value += values[i];
  }

  handleCommand(element, command, value);
}

void handleCommand(int element, int command, int value) {

  if (element == _system) {
    handleSystem(command, value);

  } else if (element == laser) {
    handleLaser(command, value);

  } else if (element >= 0 && element <= 2) {
    handleServoC(element, command, value);

  } else if (element == S0) {
    handleStepper(command, value);

  } else if (element >= 11 && element <= 12) {
    handleServoS(element - 10, command, value);
  }
}

void handleLaser(int command, int value) {
  if (command == _setIntensity) {
    analogWrite(laserPin, value);
  }
}

void handleSystem(int command, int value) {
  if (command == _delay) {
    delay(value);

  } else if (command == _detach) {
    C[0].detach();
    C[1].detach();
    C[2].detach();
    //TODO add S[x]

  } else if (command == _attach) {
    C[0].attach(9);
    C[1].attach(10);
    C[2].attach(11);
    mCFast(0, 20);
    mCFast(1, 0);
    mCFast(2, 0);
    //TODO add S[x]
  } else if (command == _setStepDelay ) {
    stepDelay = value;
  }
}
void handleServoC(int which, int command, int value) {
  if (command == _move) {
    mC(which, value);
  } else if (command == _moveFast) {
    mCFast(which, value);
  }

}
void handleServoS(int which, int command, int value) {
  if (command == _move) {
    mS(which, value);
  } else if (command == _moveFast) {
    mSFast(which, value);
  }

}
void handleStepper(int command, int value) {
  //TODO
}
void mSFast(int which, int degree) {
  //TODO
}
void mS(int which, int degree) {
  //TODO
}

void mCFast(int which, int degree) {
  degree = mapDegree(which, degree);

  C[which].write(degree);
  lastPos[which] = degree;
}

void mC(int which, int degree) {
  degree = mapDegree(which, degree);

  for (int pos = lastPos[which]; pos < degree; pos++) {
    C[which].write(pos);
    delay(stepDelay);
  }
  for (int pos = lastPos[which]; pos > degree; pos--) {
    C[which].write(pos);
    delay(stepDelay);
  }
  lastPos[which] = degree;
}

int mapDegree(int which, int degree) {
  degree = constrain(degree, 0, 180);
  return map(degree, 0, 180, mapC[which][0], mapC[which][1]);
}

