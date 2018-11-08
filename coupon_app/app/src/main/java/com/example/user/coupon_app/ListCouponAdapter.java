package com.example.user.coupon_app;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ListCouponAdapter extends ArrayAdapter<Coupon_entity> {

    private Context context;
    private List<Coupon_entity> coupons;


    public ListCouponAdapter(@NonNull Context context, int resource, @NonNull List<Coupon_entity> objects) {
        super(context, resource, objects);
        this.context = context;
        this.coupons = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View coupon_view = inflater.inflate(R.layout.coupon_layout, parent, false);
        TextView textView_name = coupon_view.findViewById(R.id.textview_coupon_name);
        TextView textView_expire_date = coupon_view.findViewById(R.id.textview_expire_date);
        TextView textView_merchant = coupon_view.findViewById(R.id.textview_merchant);
        TextView textView_value = coupon_view.findViewById(R.id.textview_value);
        TextView textView_consume_value = coupon_view.findViewById(R.id.textview_consumerValue);

        ImageView imageView = coupon_view.findViewById(R.id.imageview_coupon);

        textView_name.setText(coupons.get(position).getCoupon_name());
        textView_expire_date.setText(coupons.get(position).getExpire_date());
        textView_merchant.setText(coupons.get(position).getCoupon_merchant());
        textView_value.setText(String.valueOf(coupons.get(position).getCoupon_value()));
        textView_consume_value.setText(String.valueOf(coupons.get(position).getCoupon_consumerValue()));

        /* TODO:put images here */
        imageView.setImageResource(R.drawable.c_logo);

        return coupon_view;
    }
}
