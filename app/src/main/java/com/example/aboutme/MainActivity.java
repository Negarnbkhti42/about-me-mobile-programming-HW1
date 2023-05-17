package com.example.aboutme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int toastDurationInMilliSeconds = 10000;

        Context context = getApplicationContext();
        Toast mToastToShow = Toast.makeText(context, R.string.welcome_text, Toast.LENGTH_LONG);

        CountDownTimer toastCountDown;
        toastCountDown = new CountDownTimer(toastDurationInMilliSeconds, 1000 /*Tick duration*/) {
            public void onTick(long millisUntilFinished) {
                mToastToShow.show();
            }
            public void onFinish() {
                mToastToShow.cancel();
            }
        };

        // Show the toast and starts the countdown
        mToastToShow.show();
        toastCountDown.start();

        TextView textView = findViewById(R.id.dataText);

        try {
            AssetManager assetManager = getAssets();
            InputStream inputStream = assetManager.open("data.txt");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            String text = new String(buffer);
            textView.setText(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}