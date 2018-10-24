package com.example.user.coupon_app;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class application_pay extends Navigation_BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_pay);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_application_pay);//設置ToolBar Title
        setSupportActionBar(toolbar);

        setUpToolBar();//使用父類別的setUpToolBar()，設置ToolBar
        CurrentMenuItem = 2;//目前Navigation項目位置
        NV.getMenu().getItem(CurrentMenuItem).setChecked(true);//設置Navigation目前項目被選取狀態
    }

}
