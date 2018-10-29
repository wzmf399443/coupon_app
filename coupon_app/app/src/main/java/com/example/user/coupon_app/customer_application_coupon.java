package com.example.user.coupon_app;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class customer_application_coupon extends Navigation_customer_baseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_coupon);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_application_coupon);//設置ToolBar Title
        setSupportActionBar(toolbar);


        setUpToolBar();//使用父類別的setUpToolBar()，設置ToolBar
        CurrentMenuItem = 1;//目前Navigation項目位置
        NV.getMenu().getItem(CurrentMenuItem).setChecked(true);//設置Navigation目前項目被選取狀態
    }
}
