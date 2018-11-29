package com.example.user.coupon_app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.coupon_app.NFC.nfc_base;
import com.example.user.coupon_app.Util.Identity;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONException;
import org.json.JSONObject;

public class sending_coupon extends nfc_base {
    TextView textView_sending_coupon_name;
    String complete_message;
    ImageView image_qrcode;
    int choose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sending_coupon);
        textView_sending_coupon_name = findViewById(R.id.textView_sending_coupon_name);
        initView();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        image_qrcode = findViewById(R.id.imageView_qrcode);

        String method = getIntent().getStringExtra("method");
        switch (method) {
            case "coupon_receive":
                toolbar.setTitle(R.string.title_gift);//設置ToolBar Title
                CurrentMenuItem = 2;//目前Navigation項目位置
                setSupportActionBar(toolbar);
                setUpToolBar();//使用父類別的setUpToolBar()，設置ToolBar
                NV.getMenu().getItem(CurrentMenuItem).setChecked(true);//設置Navigation目前項目被選取狀態
                this.coupon_receive();
                break;
            case "coupon_pay":
                toolbar.setTitle(R.string.title_application_pay);//設置ToolBar Title
                this.coupon_pay();
                break;
            case "coupon_obtain_coupon":
                toolbar.setTitle(R.string.title_application_coupon);//設置ToolBar Title
                CurrentMenuItem = 1;//目前Navigation項目位置
                setSupportActionBar(toolbar);
                setUpToolBar();//使用父類別的setUpToolBar()，設置ToolBar
                NV.getMenu().getItem(CurrentMenuItem).setChecked(true);//設置Navigation目前項目被選取狀態
                this.coupon_obtain_coupon();
                break;
        }
    }

    private void barcode_generatge(String content) {
        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(content, BarcodeFormat.QR_CODE, 400, 400);
            image_qrcode.setImageBitmap(bitmap);
        } catch (Exception e) {

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {//捕捉返回鍵
        if (CurrentMenuItem != 1) {
            if ((keyCode == KeyEvent.KEYCODE_BACK)) {
                sending_coupon.this.finish();//關閉activity
                Intent intent = new Intent();
                intent.setClass(sending_coupon.this, home.class);
                startActivity(intent);
                return true;
            }
        } else {

        }
        return super.onKeyDown(keyCode, event);
    }

    private void coupon_receive() {
        JSONObject json = new JSONObject();
        try {
            json.put("ContractAddress", Identity.getContractAddress());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        barcode_generatge(json.toString());
        this.message = new String[]{json.toString()};
        this.complete_message = "send complete";
        textView_sending_coupon_name.setText("等待對方點選發送coupon");
    }

    private void coupon_pay() {
        JSONObject json = new JSONObject();
        try {
            json.put("ContractAddress", Identity.getContractAddress());
            json.put("coupon", getIntent().getStringExtra("coupon"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        barcode_generatge(json.toString());
        this.message = new String[]{json.toString()};
        this.complete_message = "pay complete";
        textView_sending_coupon_name.setText("靠上NFC裝置後點選發送coupon");
    }

    private void coupon_obtain_coupon() {
        JSONObject json = new JSONObject();
        try {
            json.put("ContractAddress", Identity.getContractAddress());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        barcode_generatge(json.toString());
        this.message = new String[]{json.toString()};
        this.complete_message = "obtain coupon complete";
        textView_sending_coupon_name.setText("靠上NFC裝置 並點選送出身分給店家");
    }

    @Override
    public void signalResult() {
        // this will be triggered when NFC message is sent to a device.
        // should be triggered on UI thread. We specify it explicitly
        // cause onNdefPushComplete is called from the Binder thread
        runOnUiThread(() ->
                Toast.makeText(this, this.complete_message, Toast.LENGTH_SHORT).show());
        Intent intent = new Intent().setClass(sending_coupon.this, home.class);
        startActivity(intent);
    }
}
