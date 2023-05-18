package com.example.aboutme;

import static java.net.Proxy.Type.HTTP;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
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

        ImageButton callButton = findViewById(R.id.phoneButton);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:123456789"));

                try {
                    startActivity(callIntent);
                } catch (Exception e) {
                    Toast.makeText(context, "exception occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ImageButton emailButton = findViewById(R.id.emailButton);
        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"norob35@gmail.com"});
                try {
                    startActivity(Intent.createChooser(intent, ""));
                } catch (Exception e) {
                    Toast.makeText(context, "exception occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}