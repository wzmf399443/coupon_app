package com.example.user.coupon_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.user.coupon_app.Util.Identity;

public class coupon_details extends AppCompatActivity {
    private String TAG = "coupon details";
    private Button btn_qrcode, btn_nfc;
    private Coupon_entity entity;
    private PopupWindow popupWindow;
    private Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_details);
        issue_coupon();
        btn_qrcode = findViewById(R.id.button_qrcode);
        btn_nfc = findViewById(R.id.button_nfc);

        /* TODO:implement QRcode */
        btn_qrcode.setOnClickListener(view -> {
        });

        btn_nfc.setOnClickListener(view -> {
            choose();
        });
    }

    private void choose() {
        intent.putExtra("coupon", this.entity.getCouponAddress());
        if (Identity.getIdentity().equals(getString(R.string.id_customer))) {
            showPopup();
        } else {
            intent.putExtra("method", "coupon_issue_for");
            intent.setClass(this, accept_coupon.class);
            startActivity(intent);
        }

    }

    private void showPopup() {
        try {
            // We need to get the instance of the LayoutInflater
            View view = LayoutInflater.from(this).inflate(R.layout.popup, null);
            popupWindow = new PopupWindow(this);
            popupWindow.setContentView(view);
            popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

            View rootView = LayoutInflater.from(this).inflate(R.layout.activity_coupon_details, null);
            popupWindow.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
            popupWindow.setOutsideTouchable(true);

            TextView text_message = view.findViewById(R.id.textView);
            text_message.setText("支付/贈送?");

            Button btn1 = view.findViewById(R.id.button_yes);
            btn1.setOnClickListener(btn1_button);
            btn1.setText("支付優惠券");

            Button btn2 = view.findViewById(R.id.button_no);
            btn2.setOnClickListener(btn2_button);
            btn2.setText("贈送優惠券");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener btn1_button = new View.OnClickListener() {
        public void onClick(View v) {
            intent.putExtra("method", "coupon_pay");
            intent.setClass(coupon_details.this, sending_coupon.class);
            popupWindow.dismiss();
            startActivity(intent);
        }
    };

    private View.OnClickListener btn2_button = new View.OnClickListener() {
        public void onClick(View v) {
            intent.putExtra("method", "coupon_send");
            intent.setClass(coupon_details.this, accept_coupon.class);
            popupWindow.dismiss();
            startActivity(intent);
        }
    };

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

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View coupon_view = inflater.inflate(R.layout.content_coupon_layout, null, false);
        ListCouponAdapter.set_coupon_layout_view(coupon_view, entity);
    }
}
