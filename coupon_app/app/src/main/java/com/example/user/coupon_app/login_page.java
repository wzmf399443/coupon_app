package com.example.user.coupon_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.user.coupon_app.Util.Api_handler;
import com.example.user.coupon_app.Util.Identity;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.user.coupon_app.Util.utils;
import com.example.user.coupon_app.customer.customer_home;
import com.example.user.coupon_app.store.store_home;

public class login_page extends AppCompatActivity {
    EditText edit_account, edit_password;
    TextView status;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        edit_account = findViewById(R.id.editText2);
        edit_password = findViewById(R.id.editText3);
        status = findViewById(R.id.textView5);

        Intent intent = getIntent();
        if(intent.getBooleanExtra("is_register",false)){
            this.edit_account.setText(intent.getStringExtra("account"));
            this.edit_password.setText(intent.getStringExtra("password"));
            this.login_change(findViewById(R.layout.activity_login_page));
        }

    }

    public void register(View view) {
        Intent intent = new Intent();
        intent.setClass(login_page.this, register.class);
        startActivity(intent);
    }

    public void login_change(View view) {
        try {
            String account = edit_account.getText().toString();
            String password = edit_password.getText().toString();
            if (this.login(account,password)) {
                if (Identity.getIdentity().equals(getString(R.string.id_customer))) {
                    Intent intent = new Intent();
                    intent.setClass(login_page.this, customer_home.class);
                    startActivity(intent);
                } else if (Identity.getIdentity().equals(getString(R.string.id_store))) {
                    Intent intent = new Intent();
                    intent.setClass(login_page.this, store_home.class);
                    startActivity(intent);
                } else {
                    utils.set_text_error_message(view, this.status, getString(R.string.login_failed));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            utils.set_text_error_message(view, this.status, getString(R.string.login_failed));
        }
    }


    private boolean login(String account,String password) throws JSONException {
        JSONObject resp;
        if (Identity.getIdentity().equals(getString(R.string.id_store))) {
            resp = Api_handler.merchant_login(account, password);
        } else {
            resp = Api_handler.consumer_login(account, password);
        }
        Identity.setToken(resp.getString(getString(R.string.response_token)));
        return resp.getBoolean(getString(R.string.response_success));
    }
}
