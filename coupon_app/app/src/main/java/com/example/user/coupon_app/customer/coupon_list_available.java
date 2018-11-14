package com.example.user.coupon_app.customer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.coupon_app.R;

public class coupon_list_available extends android.support.v4.app.Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_coupon_list_available, container, false);
        return view;
    }

    public static coupon_list_available newInstance(String text) {

        coupon_list_available f = new coupon_list_available();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
}
