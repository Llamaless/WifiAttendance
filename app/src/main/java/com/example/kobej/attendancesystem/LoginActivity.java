package com.example.kobej.attendancesystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        onButtonPress();
    }

    public boolean valid(){
        EditText username, password;
        username = findViewById(R.id.inputUsername);
        password = findViewById(R.id.inputPassword);
        String user = username.toString();
        String pass = password.toString();
        boolean validation;

        if(user.equals("davisk14")){
            if(pass.equals("password")){
                validation = true;
            }else{
                Toast.makeText(getApplicationContext(),"Invalid password",Toast.LENGTH_SHORT).show();
                validation = false;
            }
        }else{
            Toast.makeText(getApplicationContext(),"Invalid Username",Toast.LENGTH_SHORT).show();
            validation = false;
        }
        return  validation;
    }

    public ArrayList carryOver(){
        EditText username, password;
        username = findViewById(R.id.inputUsername);
        password = findViewById(R.id.inputPassword);
        String user = username.toString();
        String pass = password.toString();
        ArrayList<String> carried = new ArrayList<>();
        carried.add(user);
        carried.add(pass);
        return carried;
    }

    public void onButtonPress(){
        Button login = findViewById(R.id.loginButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean validation = valid();
                if(validation == true){
                    Intent carry = new Intent(LoginActivity.this, ConnectActivity.class);
                    ArrayList<String> carried;
                    carried = carryOver();
                    carry.putStringArrayListExtra("carried", carried);
                    startActivity(carry);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"Please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
