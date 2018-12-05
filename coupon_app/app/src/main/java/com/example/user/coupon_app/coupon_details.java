package com.example.user.coupon_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.coupon_app.Util.Identity;

public class coupon_details extends Navigation_baseActivity {
    private String TAG = "coupon details";
    private Button btn_send;
    private Coupon_entity entity;
    private Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_details);
        issue_coupon();
        btn_send = findViewById(R.id.button_nfc);
        toolbar.setTitle(R.string.title_detail);//設置ToolBar Title

        btn_send.setOnClickListener(view -> choose());
        btn_send.setVisibility(View.INVISIBLE);
        if(getIntent().getIntExtra("page_number",0)==0){
            btn_send.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {//捕捉返回鍵
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            coupon_details.this.finish();//關閉activity
            Intent intent = new Intent();
            intent.setClass(coupon_details.this, home.class);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void choose() {
        intent.putExtra("coupon", this.entity.getCouponAddress());
        if (Identity.getIdentity().equals(getString(R.string.id_customer))) {
            showPopup();
        } else {
            intent.putExtra("method", "coupon_issue_for");
            intent.setClass(this, accept_coupon.class);
            coupon_details.this.finish();//關閉activity
            startActivity(intent);
        }
    }

    private void showPopup() {
        try {
            AlertDialog.Builder ad=new AlertDialog.Builder(coupon_details.this);
            ad.setTitle("傳送方式");
            ad.setMessage("支付/贈送?");
            ad.setPositiveButton("支付優惠券", new DialogInterface.OnClickListener() {//退出按鈕
                public void onClick(DialogInterface dialog, int i) {
                    intent.putExtra("method", "coupon_pay");
                    intent.setClass(coupon_details.this, sending_coupon.class);
                    coupon_details.this.finish();//關閉activity
                    startActivity(intent);
                }
            });
            ad.setNegativeButton("贈送優惠券",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int i) {
                    intent.putExtra("method", "coupon_send");
                    intent.setClass(coupon_details.this, accept_coupon.class);
                    coupon_details.this.finish();//關閉activity
                    startActivity(intent);
                }
            });
            ad.show();//顯示對話框
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void issue_coupon() {
        entity = (Coupon_entity) getIntent().getSerializableExtra("coupon");
        this.set_show_coupon_message_content(entity);
    }

    private void set_show_coupon_message_content(Coupon_entity entity) {
        /* coupon details view */
        TextView textView_couponAddress = findViewById(R.id.textView_couponAddress);
        TextView textView_limit = findViewById(R.id.textView_limit);
        TextView textView_obtainValue = findViewById(R.id.textView_obtainValue);
        TextView textView_status = findViewById(R.id.textView_status);
        TextView textView_startDate = findViewById(R.id.textView_startDate);
        TextView textView_obtainDate = findViewById(R.id.textView_obtainDate);
        TextView textView_consumeDate = findViewById(R.id.textView_consumeDate);

        textView_couponAddress.setText(entity.getCouponAddress());
        textView_limit.setText(String.valueOf(entity.getLimit()));
        textView_obtainValue.setText(String.valueOf(entity.getObtainValue()));
        textView_status.setText(String.valueOf(entity.getStatus()));
        textView_startDate.setText(entity.getStartDate());
        textView_obtainDate.setText(entity.getObtainDate());
        textView_consumeDate.setText(entity.getConsumeDate());

        ListCouponAdapter.set_coupon_layout_view(this.findViewById(android.R.id.content).getRootView(), entity);
    }
}
