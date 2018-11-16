package com.example.user.coupon_app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user.coupon_app.Util.Api_handler;
import com.example.user.coupon_app.Util.Identity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class home extends Navigation_baseActivity {

    private ViewPager myViewPager;
    private TabLayout tabLayout;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_home); //設置ToolBar Title
        setSupportActionBar(toolbar);

        setUpToolBar(); //使用父類別的setUpToolBar()，設置ToolBar
        CurrentMenuItem = 0; //目前Navigation項目位置
        NV.getMenu().getItem(CurrentMenuItem).setChecked(true); //設置Navigation目前項目被選取狀態

        myViewPager = (ViewPager) findViewById(R.id.myViewPager);
        tabLayout = (TabLayout) findViewById(R.id.TabLayout);

        List<View> vlist = new ArrayList<View>();
        View one = getLayoutInflater().inflate(R.layout.activity_coupon_list_available, null);
        View two = getLayoutInflater().inflate(R.layout.activity_coupon_list_used, null);

        vlist.add(one);
        vlist.add(two);

        myViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return vlist.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                myViewPager.addView(vlist.get(position));
                return vlist.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                myViewPager.removeView((LinearLayout) object);
            }
        });

        ListView listview = (ListView) one.findViewById(R.id.listview_coupon);
        TextView tv_list_view = (TextView) one.findViewById(R.id.textView_show);

        tabLayout.setupWithViewPager(myViewPager);
        tabLayout.getTabAt(0).setText("可使用");
        tabLayout.getTabAt(1).setText("已使用");

        List<Coupon_entity> coupons = new ArrayList<>();

        if (Identity.getIdentity().equals(getString(R.string.id_customer))) {
            Optional.ofNullable(
                    this.getCoupons(Api_handler.consumer_getCoupons())
            ).ifPresent(coupons::addAll);
        } else {
            Optional.ofNullable(this.getCoupons(Api_handler.merchant_getUsedCoupon())).ifPresent(coupons::addAll);
            Optional.ofNullable(this.getCoupons(Api_handler.merchant_getUnusedCoupon())).ifPresent(coupons::addAll);
            Optional.ofNullable(this.getCoupons(Api_handler.merchant_getNotGivenCoupon())).ifPresent(coupons::addAll);
            Optional.ofNullable(this.getCoupons(Api_handler.merchant_getHistoryCoupon())).ifPresent(coupons::addAll);
        }

        if (coupons.isEmpty()) {
            listview.setVisibility(View.INVISIBLE);
            tv_list_view.setVisibility(View.VISIBLE);
            tv_list_view.setTextColor(Color.RED);
            tv_list_view.setText(getString(R.string.listView_is_empty));
        } else {
            listview.setAdapter(new ListCouponAdapter(this, R.layout.coupon_layout, coupons));
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    Log.d("home", "coupon:" + position);
                    Intent intent = new Intent();
                    intent.putExtra("coupon", coupons.get(position));
                    intent.setClass(home.this, login_page.class);
                    startActivity(intent);
                }
            });
        }
    }

    private List<Coupon_entity> getCoupons(JSONObject list_of_coupons) {
        if (list_of_coupons != null) {
            try {
                if (list_of_coupons.getBoolean(getString(R.string.response_success))) {
                    JSONArray data = list_of_coupons.getJSONArray(getString(R.string.response_data));
                    Type type = new TypeToken<List<Coupon_entity>>() {
                    }.getType();
                    List<Coupon_entity> coupons = new Gson().fromJson(data.toString(), type);
                    return coupons;
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }
}