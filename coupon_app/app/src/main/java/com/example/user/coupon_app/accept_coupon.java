package com.example.user.coupon_app;

import android.os.Bundle;
import android.widget.TextView;

import com.example.user.coupon_app.NFC.nfc_base;

public class accept_coupon extends nfc_base {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_coupon);
        textView = findViewById(R.id.textView);
        initView();
    }

    private void coupon_receive() {
        this.set_accepting_content();
    }

    private void coupon_accept() {
        this.set_accepting_content();
    }

    private void set_accepting_content() {}

    @Override
    public void signalResult() {

    }
}
