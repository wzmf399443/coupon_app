package com.example.user.coupon_app.customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.user.coupon_app.Navigation_baseActivity;
import com.example.user.coupon_app.R;
import com.example.user.coupon_app.receive_coupon;

public class customer_wallet extends Navigation_baseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.title_wallet);//設置ToolBar Title
        setUpToolBar();//使用父類別的setUpToolBar()，設置ToolBar
        CurrentMenuItem=3;
        NV.getMenu().getItem(CurrentMenuItem).setChecked(true);//設置Navigation目前項目被選取狀態

        Button btn1 = (Button) findViewById(R.id.change_page_btn);

        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent();
                intent.setClass(customer_wallet.this,receive_coupon.class);
                startActivity(intent);
            }
        });


    }

}
