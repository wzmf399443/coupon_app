package com.example.user.coupon_app.store;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.user.coupon_app.Navigation_baseActivity;
import com.example.user.coupon_app.R;
import com.example.user.coupon_app.Util.Api_handler;
import com.example.user.coupon_app.accept_coupon;
import com.example.user.coupon_app.coupon_details;
import com.example.user.coupon_app.sending_coupon;

public class store_stop_grant extends Navigation_baseActivity {

    private PopupWindow popupWindow;
    private View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_stop_grant);

        Toolbar toolbar = findViewById(R.id.toolbar_store);
        toolbar.setTitle(R.string.title_stop_coupon);//設置ToolBar Title
        setSupportActionBar(toolbar);

        setUpToolBar();//使用父類別的setUpToolBar()，設置ToolBar
        CurrentMenuItem = 3;//目前Navigation項目位置
        NV.getMenu().getItem(CurrentMenuItem).setChecked(true);//設置Navigation目前項目被選取狀態
    }

    public void terminateCoupon(View view) {
        showPopup(view);
    }

    private void showPopup(View v) {
        try {
            AlertDialog.Builder ad=new AlertDialog.Builder(store_stop_grant.this);
            ad.setTitle("確定刪除");
            ad.setMessage("確定刪除全部優惠券嗎?");
            ad.setPositiveButton("是", new DialogInterface.OnClickListener() {//退出按鈕
                public void onClick(DialogInterface dialog, int i) {
                    Api_handler.merchant_terminateCoupon();
                    Toast.makeText(v.getContext(),"ALL coupons are deleted",Toast.LENGTH_LONG).show();
                }
            });
            ad.setNegativeButton("否",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int i) {
                    //退出不用執行任何操作
                }
            });
            ad.show();//顯示對話框
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
