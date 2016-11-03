package com.stzemo.googletest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnConnectionFailedListener {

    public static final int RC_SIGN_IN = 200;
    private String TAG = "MAINACTIVITY";

    private GoogleApiClient mGoogleApiClient;
    private SignInButton signInButton;
    private GoogleSignInOptions gso;
    private Button btnLogOut;
    private ViewPager pager;
    private MyFragmentPagerAdapter pagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setScopes(gso.getScopeArray());
        signInButton.setOnClickListener(this);

        btnLogOut = (Button) findViewById(R.id.tvLogOut);
        btnLogOut.setOnClickListener(this);

        pager = (ViewPager) findViewById(R.id.vpMain);
        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);

        btnLogOut.setVisibility(View.GONE);
        signInButton.setVisibility(View.VISIBLE);
//        updateUI(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            case R.id.tvLogOut:
                logOut();
                break;
        }
    }

    private void logOut() {
        Log.d(TAG, "logOut");
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        updateUI(false);
                    }
                });
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            pagerAdapter.getPageLogInOut().loadData(getApplicationContext(), acct.getDisplayName(), acct.getEmail(), acct.getPhotoUrl().toString());
            updateUI(true);
            Toast.makeText(this, "LOGGED", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "FAILED", Toast.LENGTH_SHORT).show();
            // Signed out, show unauthenticated UI.
//            updateUI(false);
        }
    }

    private void updateUI(boolean update) {
        pagerAdapter.getPageLogInOut().updateUI(update);
        if (update) {
            btnLogOut.setVisibility(View.VISIBLE);
            signInButton.setVisibility(View.GONE);
        } else {
            btnLogOut.setVisibility(View.GONE);
            signInButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {

        SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return PageLogInOut.newInstance();
                case 1:
                    return PageGenerate.newInstance();
                default:
                    return null;
            }
        }

        public PageLogInOut getPageLogInOut() {
            return (PageLogInOut) registeredFragments.get(0);
        }

        public PageGenerate getPageGenerateService() {
            return (PageGenerate) registeredFragments.get(1);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            registeredFragments.put(position, fragment);
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            registeredFragments.remove(position);
            super.destroyItem(container, position, object);
        }

    }
}
