package com.ood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.util.EncodingUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ChangePassword extends AppCompatActivity {

    private TextView vNewPassword;
    private TextView vOldPassword;
    private TextView vCheckNewPassword;
    private Button btnRest;
    private String userName = "1001";
    private TextView vMessage;
    private String nameString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        Intent intent = getIntent();
        Context context = this.getApplicationContext();
        vOldPassword = findViewById(R.id.old_password_edit);
        vNewPassword = findViewById(R.id.new_passwd_edit);
        vCheckNewPassword = findViewById(R.id.check_new_password_edit);
        btnRest = (Button) findViewById(R.id.reset_password_button);
        userName = intent.getStringExtra("ID");
        nameString = intent.getStringExtra("nameString");

        vMessage = findViewById(R.id.message);

        btnRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassword = vOldPassword.getText().toString();
                String newPassword = vNewPassword.getText().toString();
                String checkNewPassword = vCheckNewPassword.getText().toString();
                Map<String, String> userData = new HashMap<>();
                try {
                    userData = readFile("userData");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (userData != null && userData.get(nameString).equals(oldPassword)) {
                    if (newPassword.equals(checkNewPassword)) {
                        userData.put(nameString, newPassword);
                        try {
                            Util.writedMap2File("userData", userData, context);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Intent back = new Intent(ChangePassword.this, LoginActivity.class);
                        try {
                            Util.writeLoginFile("loginFile", "", context);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        startActivity(back);
                    } else {
                        vMessage.setText("Conform password should match with password.");
                    }
                } else {
                    vMessage.setText("Your old password is incorrect.");
                }
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