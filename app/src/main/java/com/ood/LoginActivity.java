package com.ood;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

//import R;

import com.ImageViewdemo.RoundImageView;
import com.ood.MyAdapter;
import com.ood.R;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private RoundImageView roundImageView;
    private TextView vUserName;
    private TextView vPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        btnLogin = (Button)findViewById(R.id.login_button);
        roundImageView = (RoundImageView) findViewById(R.id.showImg);
        vPassword = findViewById(R.id.passwd_edit);
        vUserName = findViewById(R.id.username_edit);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.xiaohui);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get userData from database
                Map<String, String> userData = new HashMap<>();
                InputStream inputStream = getResources().openRawResource(R.raw.user_data);
                userData = fileReader.getUserData(inputStream);
                // fetch user input
                String userName = vUserName.getText().toString();
                String password = vPassword.getText().toString();
                // check validation
                if (userData.containsKey(userName) && userData.get(userName).equals(password)) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
