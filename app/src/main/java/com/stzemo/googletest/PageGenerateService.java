package com.stzemo.googletest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PageGenerateService extends Fragment {

    public static PageGenerateService newInstance() {
        PageGenerateService g = new PageGenerateService();
//        Bundle args = new Bundle();
//        args.putInt(ARGUMENT_PAGE_NUMBER, page);
//        pageLogInOut.setArguments(args);
        return g;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        page = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_generate_service, container, false);
        return view;
    }
}
