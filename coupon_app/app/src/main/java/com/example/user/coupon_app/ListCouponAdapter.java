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

    public static void set_coupon_layout_view(View coupon_view, Coupon_entity entity) {
        TextView textView_name = coupon_view.findViewById(R.id.textview_coupon_name);
        TextView textView_expire_date = coupon_view.findViewById(R.id.textview_expire_date);
        TextView textView_merchant = coupon_view.findViewById(R.id.textview_merchant);
        TextView textView_value = coupon_view.findViewById(R.id.textview_value);
        TextView textView_consume_value = coupon_view.findViewById(R.id.textview_consumerValue);
//        ImageView imageView = coupon_view.findViewById(R.id.imageview_coupon);

        textView_name.setText(entity.getCoupon_name());
        textView_expire_date.setText(entity.getExpire_date());
        textView_merchant.setText(entity.getCoupon_merchant());
        textView_value.setText(String.valueOf(entity.getCoupon_value()));
        textView_consume_value.setText(String.valueOf(entity.getCoupon_consumerValue()));

        /* TODO:put images here */
//        imageView.setImageResource(R.drawable.phone_coupon);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Coupon_entity entity = coupons.get(position);
        View coupon_view = inflater.inflate(R.layout.content_coupon_layout, parent, false);
        set_coupon_layout_view(coupon_view, entity);
        return coupon_view;
    }
}
