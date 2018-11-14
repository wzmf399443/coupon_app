package com.example.user.coupon_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.user.coupon_app.Util.Api_handler;
import com.example.user.coupon_app.Util.Identity;
import com.example.user.coupon_app.Util.utils;

import org.json.JSONObject;

public class register extends AppCompatActivity {

    EditText edit_account, edit_password, edit_user_name;
    TextView tv_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.edit_account = findViewById(R.id.edit_account);
        this.edit_password = findViewById(R.id.edit_pwd);
        this.edit_user_name = findViewById(R.id.edit_user_name);
        this.tv_status = findViewById(R.id.textView_status);
    }

    public void register(View v) {
        String account = edit_account.getText().toString();
        String password = edit_password.getText().toString();
        String name = edit_user_name.getText().toString();
        JSONObject resp;
        if (Identity.getIdentity().equals(getString(R.string.id_store))) {
            resp = Api_handler.merchant_register(account, password, name);
        } else {
            resp = Api_handler.consumer_register(account, password, name);
        }
        try {
            if (!resp.getBoolean(getString(R.string.response_success))) {
                utils.set_text_error_message(v, tv_status, getString(R.string.register_failed));
            } else {
                Intent intent = new Intent();
                intent.putExtra("is_register", true);
                intent.putExtra("account", account);
                intent.putExtra("password", password);
                intent.setClass(this, login_page.class);
                startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
            utils.set_text_error_message(v, tv_status, getString(R.string.register_failed));
        }
    }
}
