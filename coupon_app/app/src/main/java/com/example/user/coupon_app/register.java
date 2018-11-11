package com.example.user.coupon_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class register extends AppCompatActivity {

    EditText account,password,user_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        account=findViewById(R.id.edit_account);
        password=findViewById(R.id.edit_pwd);
        user_name=findViewById(R.id.edit_user_name);
    }

    public void registe(View v){

    }
}
