package com.example.asus.activititytest;

        import android.app.Activity;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.preference.Preference;
        import android.preference.PreferenceManager;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.view.Window;
        import android.widget.Button;
        import android.widget.CheckBox;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.sql.DataTruncation;

        import static android.R.attr.button;
        import static android.R.attr.data;
        import static android.R.attr.finishOnCloseSystemDialogs;
        import static android.R.attr.id;
        import static android.R.id.button1;
        import static android.R.id.edit;
        import static android.os.Build.VERSION_CODES.M;
        import static com.example.asus.activititytest.R.id.login;
        import static com.example.asus.activititytest.R.id.edit_text1;
        import static com.example.asus.activititytest.R.id.edit_text2;
        import static com.example.asus.activititytest.R.id.remember_password;

public class FirstActivity extends AppCompatActivity {
    private EditText nameEdit;
    private EditText passEdit;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Button login;
    private CheckBox rememberPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_layout);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        nameEdit = (EditText) findViewById(R.id.edit_text1);
        passEdit = (EditText) findViewById(R.id.edit_text2);
        rememberPass = (CheckBox) findViewById(R.id.remember_password);
        rememberPass.setChecked(true);
        login= (Button) findViewById(R.id.login);
        boolean isRemember = pref.getBoolean("remember_password",false);
        if (isRemember) {
            String username = pref.getString("username","");
            String password = pref.getString("password","");
            nameEdit.setText(username);
            passEdit.setText(password);
            rememberPass.setChecked(true);
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick (View v) {
                String username = nameEdit.getText().toString();
                String password = passEdit.getText().toString();
                if (username.equals("Erica") && password.equals("akashi")) {
                    editor = pref.edit();
                    if (rememberPass.isChecked()) {
                        editor.putBoolean("remember_password", true);
                        editor.putString("username", username);
                        editor.putString("password", password);
                    } else {
                        editor.clear();
                    }
                    editor.apply();
                    Intent intent = new Intent(FirstActivity.this,SecondActivity.class);
                    intent.putExtra("username", nameEdit.getText().toString());
                    intent.putExtra("password", passEdit.getText().toString());
                    Toast.makeText(FirstActivity.this,"登陆成功",Toast.LENGTH_SHORT).show();
                    FirstActivity.this.startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(FirstActivity.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
