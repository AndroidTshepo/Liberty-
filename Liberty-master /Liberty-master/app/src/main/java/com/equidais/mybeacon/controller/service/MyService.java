package com.equidais.mybeacon.controller.service;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import com.equidais.mybeacon.controller.MainApplication;
import com.equidais.mybeacon.controller.common.Constant;

/**
 * Created by Sensoro on 15/4/9.
 */
public class MyService extends Service {
    private static final String TAG = MyService.class.getSimpleName();

    private MainApplication application;
    private BluetoothBroadcastReceiver bluetoothBroadcastReceiver;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        application = (MainApplication) getApplication();
        bluetoothBroadcastReceiver = new BluetoothBroadcastReceiver();
        registerReceiver(bluetoothBroadcastReceiver, new IntentFilter(Constant.BLE_STATE_CHANGED_ACTION));

        if (application.isBluetoothEnabled()){
            application.startSensoroSDK();
        } else {
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//            bluetoothAdapter.enable();j
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(bluetoothBroadcastReceiver);
    }

    class BluetoothBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Constant.BLE_STATE_CHANGED_ACTION)){
                if (application.isBluetoothEnabled()){
                    application.startSensoroSDK();
                }else{
                    application.stopSensoroSDK();
                }
            }
        }
    }



}
