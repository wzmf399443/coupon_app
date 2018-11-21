package com.example.user.coupon_app;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.coupon_app.Util.Api_handler;

import java.util.Date;

public class accept_coupon extends AppCompatActivity {
    TextView textView;
    private String TAG = "NFC";
    private static String method;
    private static String coupon;
    private static int quantity, value;

    TextView tv_use_message, tv_quantity, tv_value;
    EditText ed_quantity, ed_value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_coupon);
        textView = findViewById(R.id.textView);

        tv_use_message = findViewById(R.id.textView_message);
        tv_quantity = findViewById(R.id.textView_quantity);
        tv_value = findViewById(R.id.textView_value);

        ed_quantity = findViewById(R.id.editText_quantity);
        ed_value = findViewById(R.id.editText_value);

        tv_quantity.setVisibility(View.INVISIBLE);
        tv_value.setVisibility(View.INVISIBLE);
        ed_quantity.setVisibility(View.INVISIBLE);
        ed_value.setVisibility(View.INVISIBLE);

        String tmethod = getIntent().getStringExtra("method");
        if (tmethod != null) {
            method = tmethod;
            switch (method) {
                case "coupon_send":
                    this.coupon_send();
                    break;
                case "coupon_accept":
                    this.coupon_accept();
                    break;
                case "coupon_issue_for":
                    this.coupon_issue_for();
                    break;
            }
        }
    }

    private void coupon_send() {
        coupon = getIntent().getStringExtra("coupon");
    }

    private void coupon_accept() {
        tv_value.setVisibility(View.VISIBLE);
        ed_value.setVisibility(View.VISIBLE);
        tv_value.setText("消費金額");
    }

    private void coupon_issue_for() {
        tv_value.setVisibility(View.VISIBLE);
        ed_value.setVisibility(View.VISIBLE);
        tv_quantity.setVisibility(View.VISIBLE);
        ed_quantity.setVisibility(View.VISIBLE);
        tv_value.setText("獲取金額");
        tv_quantity.setText("數量");
        coupon = getIntent().getStringExtra("coupon");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Check to see that the Activity started due to an Android Beam
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
            processNFCData(getIntent());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        value = Integer.valueOf(ed_value.getText().toString().isEmpty() ? "0" : ed_value.getText().toString());
        quantity = Integer.valueOf(ed_quantity.getText().toString().isEmpty() ? "0" : ed_quantity.getText().toString());
    }

    private void processNFCData(Intent inputIntent) {

        Log.i(TAG, "processNFCData");
        Parcelable[] rawMessages =
                inputIntent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

        if (rawMessages != null && rawMessages.length > 0) {
            NdefMessage[] messages = new NdefMessage[rawMessages.length];
            for (int i = 0; i < rawMessages.length; i++) {
                messages[i] = (NdefMessage) rawMessages[i];
            }

            Log.i(TAG, "message size = " + messages.length);

            // only one message sent during the beam
            NdefMessage msg = (NdefMessage) rawMessages[0];

            switch (method) {
                case "coupon_send":
                    String consumer = new String(msg.getRecords()[0].getPayload());
                    Api_handler.consumer_transfer(consumer, coupon);
                    Toast.makeText(this, "coupon send success", Toast.LENGTH_LONG).show();
                    break;
                case "coupon_accept":
                    String coupon = new String(msg.getRecords()[0].getPayload());
                    String customer_id = new String(msg.getRecords()[1].getPayload());
                    Api_handler.merchant_confirmCouponPay(value, new Date().toString(), coupon, customer_id);
                    Toast.makeText(this, "coupon accept success", Toast.LENGTH_LONG).show();
                    break;
                case "coupon_issue_for":
                    String consumer_id = new String(msg.getRecords()[0].getPayload());
                    Log.d(TAG, "Get client id:" + consumer_id);
                    Api_handler.merchant_grant(consumer_id, quantity, new Date().toString(), "", value);
                    Toast.makeText(this, "issue coupon success", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }
}
