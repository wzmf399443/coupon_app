package com.example.user.coupon_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class store_drawer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_drawer);
//        setUpToolBar();//使用父類別的setUpToolBar()，設置ToolBar
//    CurrentMenuItem = 0;//目前Navigation項目位置
//        NV.getMenu().getItem(CurrentMenuItem).setChecked(true);//設置Navigation目前項目被選取狀態
    }
}
