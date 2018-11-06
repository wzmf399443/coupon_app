package com.example.user.coupon_app;

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

import com.example.user.coupon_app.Util.Identity;

public class login_page extends AppCompatActivity {
    EditText edit_account, edit_password;
    TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        edit_account = findViewById(R.id.editText2);
        edit_password = findViewById(R.id.editText3);
        status = findViewById(R.id.textView5);
    }


    public void login_change(View view){
        try {
            if (this.login()){
                if (Identity.getIdentity()=="customer") {
                    Intent intent = new Intent();
                    intent.setClass(login_page.this, customer_home.class);
                    startActivity(intent);
                }
                if (Identity.getIdentity()=="store") {
                    Intent intent = new Intent();
                    intent.setClass(login_page.this, store_home.class);
                    startActivity(intent);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        status.setText(getString(R.string.login_failed));
        status.setTextColor(Color.RED);
        status.setVisibility(view.VISIBLE);
        }


    private boolean login() throws JSONException {
        String account = edit_account.getText().toString();
        String password = edit_password.getText().toString();
        JSONObject resp;
        if (Identity.getIdentity().equals(getString(R.string.id_store))) {
            resp = new Api_handler().merchant_login(account, password);
        } else {
            resp = new Api_handler().consumer_login(account, password);
        }
        Identity.setToken(resp.getString(getString(R.string.response_token)));
        return resp.getBoolean(getString(R.string.response_status));
    }
}
