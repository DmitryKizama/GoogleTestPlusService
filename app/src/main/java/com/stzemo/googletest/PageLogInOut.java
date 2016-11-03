package com.stzemo.googletest;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class PageLogInOut extends Fragment {

//    private static final String ARGUMENT_PAGE_NUMBER = "PAGENUMBER";
//    int page;

    private TextView tvName;
    private TextView tvEmail;
    private ImageView imView;
    private TextView textvName;
    private TextView textvEmail;

    public static PageLogInOut newInstance() {
        PageLogInOut pageLogInOut = new PageLogInOut();
//        Bundle args = new Bundle();
//        args.putInt(ARGUMENT_PAGE_NUMBER, page);
//        pageLogInOut.setArguments(args);
        return pageLogInOut;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        page = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_log_in_out, container, false);
        tvName = (TextView) view.findViewById(R.id.tvName);
        tvEmail = (TextView) view.findViewById(R.id.tvEmail);
        imView = (ImageView) view.findViewById(R.id.imProfile);
        textvName = (TextView) view.findViewById(R.id.textvName);
        textvEmail = (TextView) view.findViewById(R.id.textvEmail);
        updateUI(false);

        return view;
    }

    public void loadData(Context con, String name, String email, String photourl) {
        Glide.with(con).load(photourl)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imView);
        tvEmail.setText(email);
        tvName.setText(name);
    }

    public void updateUI(boolean update) {
        if (update) {
            tvEmail.setVisibility(View.VISIBLE);
            tvName.setVisibility(View.VISIBLE);
            tvEmail.setVisibility(View.VISIBLE);
            imView.setVisibility(View.VISIBLE);
            textvName.setVisibility(View.VISIBLE);
            textvEmail.setVisibility(View.VISIBLE);
        } else {
            tvEmail.setVisibility(View.GONE);
            tvName.setVisibility(View.GONE);
            tvEmail.setVisibility(View.GONE);
            imView.setVisibility(View.GONE);
            textvName.setVisibility(View.GONE);
            textvEmail.setVisibility(View.GONE);
        }
    }

}
