package com.example.user.coupon_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class start_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
    }
    public void go_cusmer(View v){
        Intent cusumer = new Intent();
        cusumer.setClass(start_page.this,login_page.class);
        startActivity(cusumer);
    }
    public void go_store(View v){
        Intent store = new Intent();
        store.setClass(start_page.this,login_page.class);
        startActivity(store);
    }
}
