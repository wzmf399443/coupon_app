package com.example.user.coupon_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.coupon_app.NFC.nfc_base;
import com.example.user.coupon_app.Util.Identity;

public class sending_coupon extends nfc_base {
    TextView textView_sending_coupon_name;
    String complete_message;
    Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sending_coupon);
        textView_sending_coupon_name = findViewById(R.id.textView_sending_coupon_name);
        initView();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        String method = getIntent().getStringExtra("method");
        switch(method){
            case "coupon_send":
                this.coupon_send();
                break;
            case "coupon_pay":
                this.coupon_pay();
                break;
            case "coupon_obtain_coupon":
                toolbar.setTitle(R.string.title_application_coupon);//設置ToolBar Title
                CurrentMenuItem = 1;//目前Navigation項目位置
                this.coupon_obtain_coupon();
                break;
        }
        setSupportActionBar(toolbar);
        setUpToolBar();//使用父類別的setUpToolBar()，設置ToolBar
        NV.getMenu().getItem(CurrentMenuItem).setChecked(true);//設置Navigation目前項目被選取狀態
    }


    private void coupon_send() {
        Intent intent = getIntent();
        String[] message = {intent.getStringExtra("coupon")};
        this.message = message;
        this.complete_message="send complete";
        this.intent.setClass(this,home.class);
        this.set_sending_message_text(message[0]);
    }

    private void coupon_pay() {
        Intent intent = getIntent();
        String[] message = {intent.getStringExtra("coupon"), Identity.getToken()};
        this.message = message;
        this.complete_message="pay complete";
        this.intent.setClass(this,home.class);
        this.set_sending_message_text(message[0]);
    }

    private void coupon_obtain_coupon() {


        String[] message = {Identity.getToken()};
        this.message = message;
        this.complete_message="obtain coupon complete";
        this.intent.setClass(this,home.class);
        textView_sending_coupon_name.setText("getting coupon...");
    }

    private void set_sending_message_text(String coupon_name) {
        /* show help message */
        textView_sending_coupon_name.setText("sending coupon" + coupon_name);
    }

    @Override
    public void signalResult() {
        // this will be triggered when NFC message is sent to a device.
        // should be triggered on UI thread. We specify it explicitly
        // cause onNdefPushComplete is called from the Binder thread
        runOnUiThread(() ->
                Toast.makeText(this, this.complete_message, Toast.LENGTH_SHORT).show());
        startActivity(intent);
    }
}
