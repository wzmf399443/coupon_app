package com.example.user.coupon_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class test_coupon extends Navigation_customer_baseActivity {

    private IntentIntegrator scanIntegrator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_coupon);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("掃描");//設置ToolBar Title
        setUpToolBar();//使用父類別的setUpToolBar()，設置ToolBar
        CurrentMenuItem=4;
        NV.getMenu().getItem(CurrentMenuItem).setChecked(true);//設置Navigation目前項目被選取狀態

        Button bt1 = (Button) findViewById(R.id.btn_scan);
        Button bt2=(Button)findViewById(R.id.button2);


        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                scanIntegrator = new IntentIntegrator(test_coupon.this);
                scanIntegrator.setPrompt("請掃描");
                scanIntegrator.setTimeout(300000);
                scanIntegrator.initiateScan();
            }
        });
    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            if (scanningResult.getContents() != null) {
                String scanContent = scanningResult.getContents();
                if (!scanContent.equals("")) {
                    Toast.makeText(getApplicationContext(), "掃描內容: " + scanContent.toString(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, intent);
            Toast.makeText(getApplicationContext(), "發生錯誤", Toast.LENGTH_LONG).show();
        }
    }
}

