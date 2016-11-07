package com.stzemo.googletest.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.stzemo.googletest.activity.StartStopServiceFragment;

import java.util.Random;

public class NotificationService extends Service {

    public static volatile boolean serviceWork;
    private Thread thread;
    private Random random = new Random();


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        serviceWork = true;
        final int sec = intent.getIntExtra(StartStopServiceFragment.EXTRA, 2) * 1000;
        thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (serviceWork) {
                        Thread.sleep(sec);
                        if (!serviceWork) {
                            return;
                        }
                        int number = random.nextInt(100);
                        Intent actionIntent = new Intent(StartStopServiceFragment.BROADCAST_ACTION_PAGE_GENERATE);
                        actionIntent.putExtra(StartStopServiceFragment.NEWNUMBER, number);
                        sendOrderedBroadcast(actionIntent, null);
                    }
                } catch (InterruptedException e) {

                }

            }
        };
        thread.start();
        return super.onStartCommand(intent, flags, startId);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        serviceWork = false;
    }
}
