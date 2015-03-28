# AttinyBluetooth
Android app and Arduino code for controlling an LED over Bluetooth

## STEP 1: Setting up the environment

Install Arduino software: http://arduino.cc/en/Main/Software

Install Android Studio: http://developer.android.com/sdk/index.html

## STEP 2: Setting up the hardware

attiny85 pinout:
![adafruit_products_pinout](https://cloud.githubusercontent.com/assets/10324727/6881464/b36e9ab4-d537-11e4-95be-b76ac9f75334.gif)

How to program the attiny85 using an Arduino Uno: http://highlowtech.org/?p=1695

Connect the attiny to the JY-MCU Bluetooth adapter as shown:
![schematic](https://cloud.githubusercontent.com/assets/10324727/6881549/42ada8d0-d53a-11e4-8102-1591041beee5.png)

## STEP 3: Programming the attiny85

In Arduino 1.6.1, create a new sketch and copy the contents of AttinyBluetoothDemo.ino to it

Compile the sketch and send it to the attiny

## STEP 4: Programming the Android App

Start a new Android Studio project for Android 4.0.3+, then copy the contents of AttinyBluetooth.java into your main java class, modifying the package name and class name to match yours

Go to https://www.uuidgenerator.net/ to generate a new random UUID for MY_UUID

In AndroidManifest.xml, add this line before the closing manifest tag:
```java
<uses-permission android:name="android.permission.BLUETOOTH" />
```
In the activity layout xml, add a button with the name "toggleButton"

