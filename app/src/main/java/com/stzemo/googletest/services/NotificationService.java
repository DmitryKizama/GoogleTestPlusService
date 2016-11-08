package com.stzemo.googletest.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.stzemo.googletest.activity.StartStopServiceFragment;

import java.util.Random;

public class NotificationService extends Service {

    private volatile boolean serviceWork = false;
    private Thread thread;
    private Random random = new Random();
    private volatile int seconds = 2;
    private IBinder mBinder = new LocalBinder();
    private String TAG = "NotificationServiceTAG";
    private volatile int currentThreadHashCode = -1;

    public class LocalBinder extends Binder {
        public NotificationService getService() {
            return NotificationService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "on Create");

    }

    public boolean getServiceWork() {
        return serviceWork;
    }

    public void stopThread() {
        serviceWork = false;
    }

    public void changeSec(int s) {
        seconds = s;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        serviceWork = true;
        thread = new Thread() {
            @Override
            public void run() {
                Log.d(TAG, "run thread");
                try {
                    Log.d(TAG, "in block try");
                    while (serviceWork || this.hashCode() == currentThreadHashCode) {
                        Log.d(TAG, "work");
                        Thread.sleep(seconds * 1000);
                        if (!serviceWork || this.hashCode() != currentThreadHashCode) {
                            return;
                        }
                        int number = random.nextInt(100);
                        Intent actionIntent = new Intent(StartStopServiceFragment.BROADCAST_ACTION_PAGE_GENERATE);
                        actionIntent.putExtra(StartStopServiceFragment.NEWNUMBER, number);
                        sendOrderedBroadcast(actionIntent, null);
                    }
                } catch (InterruptedException e) {
                    Log.d(TAG, "in block catch");
                }

            }
        };
        currentThreadHashCode = thread.hashCode();
        thread.start();
        return super.onStartCommand(intent, flags, startId);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return mBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        serviceWork = false;
    }
}
