package com.stzemo.googletest.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.stzemo.googletest.PageGenerate;

import java.util.Random;

public class NotificationService extends Service {

    private volatile boolean work = true;
    private Handler handler = new Handler();
    private Thread thread;
    private BroadcastReceiver broadcastReceiver;
    private Random random = new Random();


    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (work) {
                        Thread.sleep(5000);
                        if (!work) {
                            return;
                        }
                        int number = random.nextInt(100);
                        Intent actionIntent = new Intent(PageGenerate.BROADCAST_ACTION_PAGE_GENERATE);
                        actionIntent.putExtra(PageGenerate.NEWNUMBER, number);
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
        work = false;
    }
}
