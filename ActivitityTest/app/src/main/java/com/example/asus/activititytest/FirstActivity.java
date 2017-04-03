package com.example.asus.activititytest;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.R.attr.button;
import static android.R.attr.finishOnCloseSystemDialogs;
import static android.R.id.edit;
import static android.os.Build.VERSION_CODES.M;
import static com.example.asus.activititytest.R.id.button_1;

public class FirstActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_layout);
        Button button= (Button) findViewById(R.id.button_1);
        editText = (EditText) findViewById(R.id.edit_text1);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(FirstActivity.this,SecondActivity.class);
        startActivity(intent );
    }
}

