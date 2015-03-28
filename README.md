# AttinyBluetooth
Android app and Arduino code for controlling an LED over Bluetooth

## STEP 1: Setting up the environment

Install Arduino software: http://arduino.cc/en/Main/Software

Install Android Studio: http://developer.android.com/sdk/index.html

## STEP 2: Setting up and programming the attiny85

![Alt text](/relative/path/to/img.jpg?raw=true "Optional Title")

## STEP 3: Programming the Android App

Start a new Android Studio project for Android 4.0.3+, then copy the contents of AttinyBluetooth.java into your main java class, modifying the package name and class name to match yours.

Go to https://www.uuidgenerator.net/ to generate a new random UUID for MY_UUID

In AndroidManifest.xml, add this line before the closing </manifest> tag:
<uses-permission android:name="android.permission.BLUETOOTH" />

In the activity layout xml, add a button with the name "toggleButton"

