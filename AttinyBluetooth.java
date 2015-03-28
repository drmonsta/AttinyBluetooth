package org.giliath.joshua.attinybluetoothcontrol;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;


public class AttinyBluetooth extends ActionBarActivity {

    private static final int REQUEST_ENABLE_BT = 5;
    // Enter random UUID from https://www.uuidgenerator.net/
    private static UUID MY_UUID = UUID.fromString("03a3e730-d4e5-11e4-8830-0800200c9a66");
    private BluetoothDevice mBluetoothDevice;
    private BluetoothSocket mBluetoothSocket;
    private BluetoothSocket fallbackSocket;
    private OutputStream mBluetoothOutputStream;
    private Set<BluetoothDevice> pairedDevices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attiny_bluetooth);

        // Find Bluetooth adapter
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            System.out.println("NO BLUETOOTH SUPPORT");
        }
        
        // Enable Bluetooth adapter
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        pairedDevices = mBluetoothAdapter.getBondedDevices();
        // If there are paired devices
        if (pairedDevices.size() > 0) {
            List pairedDeviceList = new ArrayList();
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                // Add the name and address to an array adapter to show in the alert dialog
                pairedDeviceList.add(device.getName() + "\n" + device.getAddress());
            }

            // Build an alert dialog to select the Bluetooth adapter from a list of paired devices
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select the attiny85 Bluetooth adapter");
            builder.setItems((CharSequence[]) pairedDeviceList.toArray(new
                    CharSequence[pairedDeviceList.size()]), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Get selected paired Bluetooth device
                    mBluetoothDevice = (BluetoothDevice)pairedDevices.toArray()[which];
                    System.out.println(mBluetoothDevice.getAddress());
                    try {
                        // Try to connect to Bluetooth device
                        mBluetoothSocket =
                                mBluetoothDevice.createInsecureRfcommSocketToServiceRecord(MY_UUID);
                        mBluetoothSocket.connect();
                        mBluetoothOutputStream = mBluetoothSocket.getOutputStream();
                        mBluetoothOutputStream.write(new String("*").getBytes());
                    } catch (IOException e) {
                        try {
                            // Try to connect to Bluetooth device again in case of error
                            Class<?> clazz = mBluetoothSocket.getRemoteDevice().getClass();
                            Class<?>[] paramTypes = new Class<?>[]{Integer.TYPE};

                            Method m = clazz.getMethod("createRfcommSocket", paramTypes);
                            Object[] params = new Object[]{Integer.valueOf(1)};

                            fallbackSocket = (BluetoothSocket)
                                    m.invoke(mBluetoothSocket.getRemoteDevice(), params);
                            fallbackSocket.connect();
                            mBluetoothOutputStream = fallbackSocket.getOutputStream();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        } catch (InvocationTargetException e1) {
                            e1.printStackTrace();
                        } catch (NoSuchMethodException e1) {
                            e1.printStackTrace();
                        } catch (IllegalAccessException e1) {
                            e1.printStackTrace();
                        }
                        e.printStackTrace();

                    }
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }

        // Handle button click to toggle LED
        this.findViewById(R.id.toggleButton).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    mBluetoothOutputStream.write((int)'1');
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_attiny_bluetooth, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
