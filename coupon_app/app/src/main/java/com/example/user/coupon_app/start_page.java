package com.example.user.coupon_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.user.coupon_app.Util.Api_handler;
import com.example.user.coupon_app.Util.Identity;
import com.example.user.coupon_app.customer.customer_wallet;

public class start_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        new Api_handler(this);
    }
    public void go_cusmer(View v){

        Identity.setIdentity(getString(R.string.id_customer));
        Intent cusumer = new Intent();
        cusumer.setClass(start_page.this,customer_wallet.class);
        startActivity(cusumer);
    }
    public void go_store(View v){

        Identity.setIdentity(getString(R.string.id_store));
        Intent store = new Intent();
        store.setClass(start_page.this,login_page.class);
        startActivity(store);
    }
}
