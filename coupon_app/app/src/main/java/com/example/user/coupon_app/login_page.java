package com.example.user.coupon_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class login_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
    }

    public void login_change_customer(View view){
        Intent intent =new Intent();
        intent.setClass(login_page.this,customer_home.class);
        startActivity(intent);
    }
    public void login_change_store(View view){
        Intent intent =new Intent();
        intent.setClass(login_page.this,store_home.class);
        startActivity(intent);
    }
}
