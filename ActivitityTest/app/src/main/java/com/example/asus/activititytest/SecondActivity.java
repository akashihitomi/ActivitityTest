package com.example.asus.activititytest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.breadCrumbShortTitle;
import static android.R.attr.data;
import static com.example.asus.activititytest.R.id.image;
import static com.example.asus.activititytest.R.id.login;
import static com.example.asus.activititytest.R.id.takephoto;


public class SecondActivity extends AppCompatActivity {
    private Button setting;
    private Button ToList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        setting = (Button) findViewById(R.id.tophoto);
        ToList = (Button) findViewById(R.id.toList);
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String password = intent.getStringExtra("password");
        Log.d("SecondActivity", username);
        Log.d("SecondActivity", password);
        TextView nameTextView = (TextView) findViewById(R.id.remembername);
        TextView passTextView = (TextView) findViewById(R.id.rememberpass);
        nameTextView.setText(username);
        passTextView.setText(password);

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick (View v) {
                Intent intent = new Intent(SecondActivity.this,CameraTest.class);
                SecondActivity.this.startActivity(intent);
            }
        });

        ToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, List_View.class);
                SecondActivity.this.startActivity(intent);
            }
        });
    }
}