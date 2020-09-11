package com.example.gadsleaderboard.ui.splash;

import android.content.Intent;
import android.os.Bundle;

import com.example.gadsleaderboard.R;
import com.example.gadsleaderboard.ui.home.HomeActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;

import java.util.concurrent.TimeUnit;

public class SplashActivity extends AppCompatActivity {

    private long SPLASH_TIMEOUT = TimeUnit.SECONDS.toMillis(3);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(this::startApp, SPLASH_TIMEOUT);
    }

    private void startApp() {
        startActivity(new Intent(this, HomeActivity.class));
    }

}