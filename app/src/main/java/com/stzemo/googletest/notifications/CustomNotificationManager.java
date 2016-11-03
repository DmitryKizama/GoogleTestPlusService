package com.stzemo.googletest.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.stzemo.googletest.App;
import com.stzemo.googletest.PageGenerate;
import com.stzemo.googletest.R;

public class CustomNotificationManager {
    private BroadcastReceiver broadcastReceiver;
    public static final String BROADCAST_ACTION_PAGE_GENERATE = "BROADCASTACTIONPAGEGENERATE";
    public static final String NEWNUMBER = "NEWNUMBER";

    public void create() {
        broadcastReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                Log.d("dfdsfsdfds", "get");
                int number = intent.getIntExtra(NEWNUMBER, 0);
                sendNotification(number);
                abortBroadcast();
            }
        };
        IntentFilter intFilt = new IntentFilter(BROADCAST_ACTION_PAGE_GENERATE);
        intFilt.setPriority(1);
        App.appContext.registerReceiver(broadcastReceiver, intFilt);
    }


    private void sendNotification(int number) {
        NotificationManager nm = (NotificationManager) App.appContext.getSystemService(App.appContext.NOTIFICATION_SERVICE);
        Notification myNotication;

        Intent intent = new Intent(App.appContext, PageGenerate.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(App.appContext, 1, intent, 0);
        Notification.Builder builder = new Notification.Builder(App.appContext);

        builder.setContentTitle("TEST Notification");
        builder.setContentText("You have a new message");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentIntent(pendingIntent);
        builder.setSubText("Your new number = " + number);
        builder.setNumber(100);
        builder.build();

        myNotication = builder.getNotification();
        nm.notify(11, myNotication);
    }

}
