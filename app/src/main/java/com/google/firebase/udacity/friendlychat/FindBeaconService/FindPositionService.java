package com.google.firebase.udacity.friendlychat.FindBeaconService;

import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import org.altbeacon.beacon.AltBeaconParser;
import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class FindPositionService extends Service implements BeaconConsumer {

    private final IBinder binder = new MyBinder();

    Queue<DeviceNameRssiPair> queue;
    private final String TAG = "MyServiceLog";

    public FindPositionService() {
    }

    BeaconManager beaconManager;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MyServiceLog", "create");
        beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers().add(new AltBeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
        beaconManager.bind(this);
        beaconManager.setForegroundScanPeriod(1000);
        beaconManager.setForegroundBetweenScanPeriod(500);
        try {
            beaconManager.updateScanPeriods();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        queue = new LinkedList<>();
    }

    @Override
    public void onBeaconServiceConnect() {
        Log.d(TAG, "onBeaconServiceConnect");
        beaconManager.addRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> collection, Region region) {
                int max = -200;
                String maxName = "";
                String maxBeaconId2 = "";
                for (Iterator iterator = collection.iterator(); iterator.hasNext(); ) {
                    Beacon beacon = (Beacon) iterator.next();
                    Log.d(TAG + "collection", identifyBeacon(beacon.getId2().toString()) + " rssi:" + beacon.getRssi());
                    if (beacon.getRssi() >= max) {
                        max = beacon.getRssi();
                        maxName = identifyBeacon(beacon.getId2().toString());
                        maxBeaconId2 = beacon.getId2().toString();
                    }
                }
                Log.d(TAG, "--------------------" + " " + "max:" + maxBeaconId2);
                callBack.onScanResult(maxBeaconId2);
            }
        });
        startScan();
    }

    public boolean mBeaconServiceConnected = false;

    Runnable runnable;

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("MyServiceLog", "onBind");
        return binder;
    }


    private CallBack callBack = null;

    public static interface CallBack {
        void onScanResult(String id2);
    }

    public boolean isScanning = false;

    public void startScan() {
        try {
            Log.d(TAG, "start");
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
            isScanning = true;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void stopScan() {
        try {
            beaconManager.stopRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
            isScanning = false;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public String calculateTheMost(Queue<DeviceNameRssiPair> queue) {

        Log.d(TAG, "queue" + queue.toString());
        Map<String, NumberRssiPair> collectBox = new HashMap<>();//name,(number,total rssi)

        while (!queue.isEmpty()) {

            DeviceNameRssiPair device = queue.poll();
            String name = device.toString();
            int rssi = device.RSSI;

            if (!collectBox.containsKey(name)) {

                collectBox.put(name, new NumberRssiPair(1, rssi));
                Log.d(TAG, "++f " + "name:" + name);

            } else {

                int num = collectBox.get(name).Num;
                int newRssi = collectBox.get(name).Rssi + rssi;
                collectBox.put(name, new NumberRssiPair(++num, newRssi));
                Log.d(TAG, "++ " + "name:" + name + "num:" + newRssi);

            }
        }

        Log.d(TAG, "collectBox" + collectBox.toString());
        return getTheMost(collectBox);
    }

    public String getTheMost(Map<String, NumberRssiPair> devices) {
        float maxAvgRssi = -300f;
        String maxName = null;
        for (Map.Entry<String, NumberRssiPair> device : devices.entrySet()) {
            String name = device.getKey();

            Log.d(TAG, "getTheMost" + (device.getValue().Rssi / device.getValue().Num));
            float avgRssi = device.getValue().Rssi / device.getValue().Num;
            if (avgRssi >= maxAvgRssi) {
                maxAvgRssi = avgRssi;
                maxName = name;
            }
        }

        return maxName;
    }


    class NumberRssiPair {

        public int Num;
        public int Rssi;

        public NumberRssiPair(int num, int rssi) {
            Num = num;
            Rssi = rssi;
        }

        @Override
        public String toString() {
            return "Num: " + Num + " Rssi: " + Rssi;
        }
    }


    public void startCount() {
        Thread thread = new Thread(runnable);
        thread.start();
    }

    public CallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public class MyBinder extends Binder {
        public FindPositionService getService() {
            return FindPositionService.this;
        }
    }


    public String identifyBeacon(String id2) {
        if (id2.equals("56197"))
            return "purple2";
        if (id2.equals("10136"))
            return "purple1";
        if (id2.equals("15498"))
            return "pink2";
        if (id2.equals("7407"))
            return "pink1";
        if (id2.equals("35830"))
            return "yellow2";
        if (id2.equals("53462"))
            return "yellow1";
        return "non";
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public String getNowPosition(Queue<DeviceNameRssiPair> queue) {

        Queue<DeviceNameRssiPair> queueTemp = new LinkedList<>();
        queueTemp = queue;
        return identifyBeacon(calculateTheMost(queueTemp));
    }

    public class DeviceNameRssiPair {
        public BluetoothDevice bluetoothDevice;
        public int RSSI;

        public DeviceNameRssiPair(BluetoothDevice bluetoothDevice, int RSSI) {
            this.bluetoothDevice = bluetoothDevice;
            this.RSSI = RSSI;
        }

        @Override
        public String toString() {
            return "device:" + bluetoothDevice.toString() + " rssi:" + RSSI;
        }
    }
}