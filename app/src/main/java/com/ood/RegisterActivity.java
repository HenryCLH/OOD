package com.ood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private Button btnSignUp;
    private TextView vUsername;
    private TextView vPassword;
    private TextView vMessage;
    private TextView vRepassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnSignUp = (Button)findViewById(R.id.register_button);
        vUsername = findViewById(R.id.username_edit);
        vPassword = findViewById(R.id.passwd_edit);
        vMessage = findViewById(R.id.message);
        vRepassword = findViewById(R.id.repassword_edit);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = vUsername.getText().toString();
                String password = vPassword.getText().toString();
                String rePassword = vRepassword.getText().toString();
                if (!password.equals(rePassword)) {
                    vMessage.setText("Your password and confirmation password do not match");
                } else if (!checkValidation(userName)) {
                    vMessage.setText("Please enter valid email/phone");
                } else {
                    try {
                        writeFile("userData", userName + " " + password);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                }

            }
        });

    }

    public void writeFile(String fileName,String writestr) throws IOException {
        try{
            FileOutputStream fout = openFileOutput(fileName, Context.MODE_PRIVATE);
            byte [] bytes = writestr.getBytes();
            fout.write(bytes);
            fout.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private boolean checkValidation(String input) {
        return validateEmail(input) || validatePhoneNum(input);
    }

    public static boolean validateEmail(String str){
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static boolean validatePhoneNum(String str){
        Pattern pattern = Pattern.compile("^[0-9]{10}$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }


}