#include <SoftwareSerial.h>;
SoftwareSerial Bluetooth(3, 4); 
#define LED_PIN 2
int LED_STATE = 1;

void setup() {
  Bluetooth.begin(9600);
  pinMode(LED_PIN,OUTPUT);
  digitalWrite(LED_PIN,LED_STATE);
  delay(100);
}

void loop() {
  if (Bluetooth.available()) {
    int cmd = Bluetooth.read();
    switch(cmd) {
      case '1':
        LED_STATE = !LED_STATE;
        digitalWrite(LED_PIN,LED_STATE);
        break;
      default:
        break;
    }
  }
}
