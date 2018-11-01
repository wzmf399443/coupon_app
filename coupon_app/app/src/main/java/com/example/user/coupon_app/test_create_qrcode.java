package com.example.user.coupon_app;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class test_create_qrcode extends Navigation_customer_baseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_create_qrcode);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("產生");//設置ToolBar Title
        setUpToolBar();//使用父類別的setUpToolBar()，設置ToolBar
        CurrentMenuItem =5;
        NV.getMenu().getItem(CurrentMenuItem).setChecked(true);//設置Navigation目前項目被選取狀態
    }

        public void genCode(View view) {
            ImageView ivCode = (ImageView) findViewById(R.id.imageView);
            EditText etContent = (EditText) findViewById(R.id.editText);
            BarcodeEncoder encoder = new BarcodeEncoder();
            try {
                Bitmap bit = encoder.encodeBitmap(etContent.getText().toString(), BarcodeFormat.QR_CODE,
                        250, 250);
                ivCode.setImageBitmap(bit);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }

    }


