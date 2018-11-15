package com.example.user.coupon_app.store;

import android.os.Bundle;
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

public class store_stop_grant extends Navigation_baseActivity {

    private PopupWindow popupWindow;
    private View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_stop_grant);
    }

    public void terminateCoupon(View view) {
        showPopup();
    }

    private void showPopup() {
        try {
            // We need to get the instance of the LayoutInflater
            View view = LayoutInflater.from(this).inflate(R.layout.popup,null);
            popupWindow=new PopupWindow(this);
            popupWindow.setContentView(view);
            popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            rootView =LayoutInflater.from(this).inflate(R.layout.activity_main, null);
            popupWindow.showAtLocation(rootView, Gravity.BOTTOM,0,0);
            popupWindow.setOutsideTouchable(true);
            Button yes = view.findViewById(R.id.button_yes);
            yes.setOnClickListener(yes_button);
            Button no = view.findViewById(R.id.button_no);
            no.setOnClickListener(no_button);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener yes_button= new View.OnClickListener(){
        public void onClick(View v) {
            Api_handler.merchant_terminateCoupon();
            Toast.makeText(v.getContext(),"ALL coupons are deleted",Toast.LENGTH_LONG).show();
            popupWindow.dismiss();
        }
    };

    private View.OnClickListener no_button = new View.OnClickListener() {
        public void onClick(View v) {
            popupWindow.dismiss();
        }
    };
}
