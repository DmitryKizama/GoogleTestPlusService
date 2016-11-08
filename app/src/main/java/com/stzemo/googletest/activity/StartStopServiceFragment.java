package com.stzemo.googletest.activity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.stzemo.googletest.App;
import com.stzemo.googletest.R;
import com.stzemo.googletest.dialog.SetPeriodDialog;
import com.stzemo.googletest.services.NotificationService;

public class StartStopServiceFragment extends Fragment implements View.OnClickListener, SetPeriodDialog.DialogListener {


    public static final String BROADCAST_ACTION_PAGE_GENERATE = "BROADCASTACTIONPAGEGENERATE";
    public static final String NEWNUMBER = "NEWNUMBER";

    private Button btnStart, btnStop, btnSetPeriod;
    private BroadcastReceiver broadcastReceiver;
    private TextView tv;
    private NotificationService nService;
    private boolean notifServiceBound;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            notifServiceBound = true;
            nService = ((NotificationService.LocalBinder) iBinder).getService();
            if (nService.getServiceWork()) {
                setVisibility(true);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            notifServiceBound = false;
        }
    };

    public static StartStopServiceFragment newInstance() {
        StartStopServiceFragment g = new StartStopServiceFragment();
        return g;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page_generate, container, false);
        btnStart = (Button) view.findViewById(R.id.btnStart);
        btnStop = (Button) view.findViewById(R.id.btnStop);
        btnSetPeriod = (Button) view.findViewById(R.id.btnSetPeriod);

        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);

        btnStop.setVisibility(View.GONE);
        btnSetPeriod.setOnClickListener(this);
        tv = (TextView) view.findViewById(R.id.tv);

        broadcastReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                tv.setText("New number = " + intent.getIntExtra(NEWNUMBER, 0));
                abortBroadcast();
            }
        };
        return view;
    }

    private void registerBroadcast() {
        IntentFilter intFilt = new IntentFilter(BROADCAST_ACTION_PAGE_GENERATE);
        intFilt.setPriority(2);
        App.appContext.registerReceiver(broadcastReceiver, intFilt);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!notifServiceBound) {
            bindService();
        }
        registerBroadcast();
    }

    private void setVisibility(boolean visibility) {
        if (visibility) {
            btnStop.setVisibility(View.VISIBLE);
            btnStart.setVisibility(View.GONE);
//            btnSetPeriod.setVisibility(View.GONE);
        } else {
            btnStop.setVisibility(View.GONE);
            btnStart.setVisibility(View.VISIBLE);
//            btnSetPeriod.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnStart:
                Intent intent = new Intent(view.getContext(), NotificationService.class);
                getContext().startService(intent);
                if (!notifServiceBound) {
                    bindService();
                }
                setVisibility(true);
                break;
            case R.id.btnStop:
                getContext().stopService(new Intent(view.getContext(), NotificationService.class));
//                nService.stopThread();
                unBindService();
                setVisibility(false);
                break;
            case R.id.btnSetPeriod:
                (new SetPeriodDialog(getContext(), this)).show();
                break;
        }
    }

    private void unBindService() {
        getContext().unbindService(mServiceConnection);
        notifServiceBound = false;
    }

    private void bindService() {
        Intent intent = new Intent(getContext(), NotificationService.class);
        getContext().bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onPause() {
        super.onPause();
        App.appContext.unregisterReceiver(broadcastReceiver);
        if (notifServiceBound) {
            unBindService();
        }
    }

    @Override
    public void onApplyPressed(int sec) {
        if (notifServiceBound) {
            nService.changeSec(sec);
        }
    }

}
