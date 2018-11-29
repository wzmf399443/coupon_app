package com.example.user.coupon_app;

import android.app.AlertDialog;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.coupon_app.Util.Api_handler;
import com.example.user.coupon_app.Util.Identity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class accept_coupon extends Navigation_baseActivity {
    private String TAG = "NFC";
    private static String method;
    private static String coupon;
    private static int quantity, value;

    TextView tv_use_message, tv_quantity, tv_value;
    EditText ed_quantity, ed_value;
    Button btn_qrcode;
    private IntentIntegrator scanIntegrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_coupon);

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

        btn_qrcode = findViewById(R.id.button_qrcode);

        btn_qrcode.setOnClickListener(v -> {
            scanIntegrator = new IntentIntegrator(accept_coupon.this);
            scanIntegrator.setPrompt("請掃描");
            scanIntegrator.setTimeout(300000);
            scanIntegrator.initiateScan();
        });

        if (tmethod != null) {
            method = tmethod;
            initView();
        }
    }

    private void initView() {
        switch (method) {
            case "coupon_send":
                toolbar.setTitle(R.string.title_send_coupon);//設置ToolBar Title
                this.coupon_send();
                break;
            case "coupon_accept":
                toolbar.setTitle(R.string.title_accept);//設置ToolBar Title
                CurrentMenuItem = 2;//目前Navigation項目位置
                this.coupon_accept();
                setSupportActionBar(toolbar);
                setUpToolBar();//使用父類別的setUpToolBar()，設置ToolBar
                NV.getMenu().getItem(CurrentMenuItem).setChecked(true);//設置Navigation目前項目被選取狀態
                break;
            case "coupon_issue_for":
                toolbar.setTitle(R.string.title_grant_coupon);//設置ToolBar Title
                this.coupon_issue_for();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {//捕捉返回鍵
        if (Identity.getIdentity().equals(getString(R.string.id_customer))) {
            if (CurrentMenuItem != 1) {
                if ((keyCode == KeyEvent.KEYCODE_BACK)) {
                    accept_coupon.this.finish();//關閉activity
                    Intent intent = new Intent();
                    intent.setClass(accept_coupon.this, home.class);
                    startActivity(intent);
                    return true;
                }
            } else {
            }
        } else {
            if (CurrentMenuItem == 0) {
                if ((keyCode == KeyEvent.KEYCODE_BACK)) {
                    accept_coupon.this.finish();//關閉activity
                    Intent intent = new Intent();
                    intent.setClass(accept_coupon.this, home.class);
                    startActivity(intent);
                    return true;
                }
            } else {
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    private void coupon_send() {
        coupon = getIntent().getStringExtra("coupon");
        tv_use_message.setText("靠上NFC 等待好友點選");
    }

    private void coupon_accept() {
        tv_value.setVisibility(View.VISIBLE);
        ed_value.setVisibility(View.VISIBLE);
        tv_use_message.setText("輸入完消費金額後靠上NFC請顧客點選畫面");
        tv_value.setText("消費金額");
    }

    private void coupon_issue_for() {
        tv_value.setVisibility(View.VISIBLE);
        ed_value.setVisibility(View.VISIBLE);
        tv_quantity.setVisibility(View.VISIBLE);
        ed_quantity.setVisibility(View.VISIBLE);
        tv_use_message.setText("輸入完消費金額和欲贈送數量後靠上NFC請顧客點選畫面");
        tv_value.setText("消費金額");
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
            String data = new String(msg.getRecords()[0].getPayload());
            try {
                process_sending_data(data);
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(this, "Receive nfc data failed", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            if (scanningResult.getContents() != null) {
                String scanContent = scanningResult.getContents();
                try {
                    process_sending_data(scanContent);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Receive qrcode data failed", Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, intent);
            Toast.makeText(getApplicationContext(), "發生錯誤", Toast.LENGTH_LONG).show();
        }
    }

    private void process_sending_data(String json_string) throws JSONException {
        JSONObject json = new JSONObject(json_string);
        String data1 = json.optString("ContractAddress", "");
        String data2 = json.optString("coupon", "");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String date = sdf.format(new Date());
        String message = "";
        JSONObject ret = new JSONObject();
        switch (method) {
            case "coupon_send":
                ret = Api_handler.consumer_transfer(data1, coupon);
                break;
            case "coupon_accept":
                ret = Api_handler.merchant_confirmCouponPay(value, date, data2, data1);
                break;
            case "coupon_issue_for":
                Log.d(TAG, "Get client id:" + data1);
                ret = Api_handler.merchant_grant(data1, quantity, date, "", value);
                break;
        }

        if (ret != null) {
            message = ret.optString(getString(R.string.response_message));
            new AlertDialog.Builder(this)
                    .setMessage(message)
                    .setPositiveButton("ok", (dialog, id) -> {
                    }).show();
        } else {
            Toast.makeText(this, "failed", Toast.LENGTH_LONG).show();
        }
    }
}
