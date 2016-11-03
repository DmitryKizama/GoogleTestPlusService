package com.stzemo.googletest;

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

import com.stzemo.googletest.services.NotificationService;

public class PageGenerateService extends Fragment implements View.OnClickListener {


    public static final String BROADCAST_ACTION_PAGE_GENERATE = "BROADCASTACTIONPAGEGENERATE";
    public static final String NEWNUMBER = "NEWNUMBER";

    private Button btnStart, btnStop;
    private BroadcastReceiver broadcastReceiver;
    private int number;
    private TextView tv;

    public static PageGenerateService newInstance() {
        PageGenerateService g = new PageGenerateService();
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
        btnStart.setOnClickListener(this);
        btnStop = (Button) view.findViewById(R.id.btnStop);
        btnStop.setVisibility(View.GONE);
        btnStop.setOnClickListener(this);
        tv = (TextView) view.findViewById(R.id.tv);

        broadcastReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                Log.d("dfdsfsdfds", "get");
                number = intent.getIntExtra(NEWNUMBER, 0);
                tv.setText("New number = " + number);
            }
        };
        IntentFilter intFilt = new IntentFilter(BROADCAST_ACTION_PAGE_GENERATE);
        view.getContext().registerReceiver(broadcastReceiver, intFilt);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnStart:
                Log.d("dfdsfsdfds", "work");
                view.getContext().startService(new Intent(view.getContext(), NotificationService.class));
                btnStart.setVisibility(View.GONE);
                btnStop.setVisibility(View.VISIBLE);
                break;
            case R.id.btnStop:
                view.getContext().stopService(new Intent(view.getContext(), NotificationService.class));
                btnStop.setVisibility(View.GONE);
                btnStart.setVisibility(View.VISIBLE);
                break;
        }
    }
}
