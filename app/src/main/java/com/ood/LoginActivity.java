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

import org.apache.http.util.EncodingUtils;

import java.io.FileInputStream;
import java.io.IOException;
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
    private TextView vNewAccount;
    private TextView vMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        btnLogin = (Button)findViewById(R.id.login_button);
        vNewAccount = findViewById(R.id.register);
        roundImageView = (RoundImageView) findViewById(R.id.showImg);
        vPassword = findViewById(R.id.passwd_edit);
        vUserName = findViewById(R.id.username_edit);
        vMessage = findViewById(R.id.message);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.xiaohui);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get userData from database
                Map<String, String> userData = new HashMap<>();
                try {
                    userData = readFile("userData");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // fetch user input
                String userName = vUserName.getText().toString();
                String password = vPassword.getText().toString();
                // check validation
                if ( userData != null && userData.size() != 0 &&
                        userData.containsKey(userName) && userData.get(userName).equals(password)) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        String hashcode = String.valueOf(userName.hashCode());
                        intent.putExtra("userName", hashcode);
                        startActivity(intent);
                } else {
                    vMessage.setText("Oops! You email/phone or password is incorrect");
                }
            }
        });

        vNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }


    public Map<String, String> readFile(String fileName) throws IOException {
        String res="";
        try{
            FileInputStream fin = openFileInput(fileName);
            int length = fin.available();
            byte [] buffer = new byte[length];
            fin.read(buffer);
            //设置编码格式
            res = EncodingUtils.getString(buffer, "UTF-8");
            fin.close();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        String[] rawData = res.split("\n");
        Map<String, String> map = new HashMap<>();
        for (String data : rawData) {
            String[] temp = data.split(" ");
            map.put(temp[0], temp[1]);
        }
        return map;
    }
}
