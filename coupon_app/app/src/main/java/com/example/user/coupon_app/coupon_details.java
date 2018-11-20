package com.example.user.coupon_app;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.coupon_app.NFC.nfc_base;

public class coupon_details extends nfc_base {
    String TAG = "coupon details";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_details);
        initView();
        issue_coupon();
    }

    private void issue_coupon() {
        Coupon_entity entity = (Coupon_entity) getIntent().getSerializableExtra("coupon");
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

        TextView textView_name = findViewById(R.id.textview_coupon_name);
        TextView textView_expire_date = findViewById(R.id.textview_expire_date);
        TextView textView_merchant = findViewById(R.id.textview_merchant);
        TextView textView_value = findViewById(R.id.textview_value);
        TextView textView_consume_value = findViewById(R.id.textview_consumerValue);
        ImageView imageView = findViewById(R.id.imageview_coupon);

        textView_couponAddress.setText(entity.getCouponAddress());
        textView_limit.setText(String.valueOf(entity.getLimit()));
        textView_obtainValue.setText(String.valueOf(entity.getObtainValue()));
        textView_status.setText(String.valueOf(entity.getStatus()));
        textView_startDate.setText(entity.getStartDate());
        textView_obtainDate.setText(entity.getObtainDate());
        textView_consumeDate.setText(entity.getConsumeDate());

        textView_name.setText(entity.getCoupon_name());
        textView_expire_date.setText(entity.getExpire_date());
        textView_merchant.setText(entity.getCoupon_merchant());
        textView_value.setText(String.valueOf(entity.getCoupon_value()));
        textView_consume_value.setText(String.valueOf(entity.getCoupon_consumerValue()));

        /* TODO:put images here */
        imageView.setImageResource(R.drawable.phone_coupon);
    }

    @Override
    public void signalResult() {}

}
