package com.example.user.coupon_app;

import android.annotation.SuppressLint;
import android.content.Intent;
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
        if (intent.getBooleanExtra("is_register", false)) {
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
            JSONObject ret = this.login(account, password);
            if (ret !=null && ret.getBoolean(getString(R.string.response_success))) {
                Identity.setToken(ret.getString(getString(R.string.response_token)));
                Identity.setContractAddress(ret.optString(getString(R.string.response_contractAddress),null));

                Intent intent = new Intent();
                intent.setClass(login_page.this, home.class);
                startActivity(intent);
            } else {
                utils.set_text_error_message(view, this.status, getString(R.string.login_failed));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            utils.set_text_error_message(view, this.status, getString(R.string.login_failed));
        }
    }


    private JSONObject login(String account, String password) throws JSONException {
        JSONObject resp;
        if (Identity.getIdentity().equals(getString(R.string.id_store))) {
            resp = Api_handler.merchant_login(account, password);
        } else {
            resp = Api_handler.consumer_login(account, password);
        }
        return resp;
    }
}
