package com.stzemo.googletest.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.stzemo.googletest.R;
import com.stzemo.googletest.services.NotificationService;

public class StartStopServiceFragment extends Fragment implements View.OnClickListener {


    public static final String BROADCAST_ACTION_PAGE_GENERATE = "BROADCASTACTIONPAGEGENERATE";
    public static final String NEWNUMBER = "NEWNUMBER";

    private Button btnStart, btnStop;
    private BroadcastReceiver broadcastReceiver;
    private TextView tv;

    public static StartStopServiceFragment newInstance() {
        StartStopServiceFragment g = new StartStopServiceFragment();
        return g;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        page = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_generate_service, container, false);
        btnStart = (Button) view.findViewById(R.id.btnStart);
        btnStop = (Button) view.findViewById(R.id.btnStop);

        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);
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
        getContext().registerReceiver(broadcastReceiver, intFilt);
    }

    @Override
    public void onResume() {
        super.onResume();
        setVisibility(NotificationService.serviceWork);
        registerBroadcast();
    }

    private void setVisibility(boolean visibility) {
        if (visibility) {
            btnStop.setVisibility(View.VISIBLE);
            btnStart.setVisibility(View.GONE);
        } else {
            btnStop.setVisibility(View.GONE);
            btnStart.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnStart:
                view.getContext().startService(new Intent(view.getContext(), NotificationService.class));
                setVisibility(true);
                break;
            case R.id.btnStop:
                view.getContext().stopService(new Intent(view.getContext(), NotificationService.class));
                setVisibility(false);
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        getContext().unregisterReceiver(broadcastReceiver);
    }

}
