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

    ListView listview;
    TextView tv_list_view;

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

        tabLayout.setupWithViewPager(myViewPager);
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initView(){
        List<View> vlist = new ArrayList<View>();

        if (Identity.getIdentity().equals(getString(R.string.id_customer))) {
            vlist.add(getLayoutInflater().inflate(R.layout.activity_coupon_list_available, null));

            listview = (ListView) vlist.get(0).findViewById(R.id.listview_coupon);
            tv_list_view = (TextView) vlist.get(0).findViewById(R.id.textView_show);
            myViewPager.setAdapter(new myadapter(vlist, myViewPager));

            List<Coupon_entity> coupons = new ArrayList<>();

            Optional.ofNullable(this.getCoupons(Api_handler.consumer_getCoupons())).ifPresent(coupons::addAll);
            setView(coupons, listview, tv_list_view);

            tabLayout.setupWithViewPager(myViewPager);
            tabLayout.getTabAt(0).setText("可使用");

        } else {
            vlist.add(getLayoutInflater().inflate(R.layout.store_coupon_available, null));
            vlist.add(getLayoutInflater().inflate(R.layout.store_coupon_unissued, null));
            vlist.add(getLayoutInflater().inflate(R.layout.store_coupon_used, null));
            vlist.add(getLayoutInflater().inflate(R.layout.store_coupon_historical, null));

            myViewPager.setAdapter(new myadapter(vlist, myViewPager));

            List<Coupon_entity> coupons_unused = new ArrayList<>();
            List<Coupon_entity> coupons_used = new ArrayList<>();
            List<Coupon_entity> coupons_notgiven = new ArrayList<>();
            List<Coupon_entity> coupons_history = new ArrayList<>();
            Optional.ofNullable(this.getCoupons(Api_handler.merchant_getUnusedCoupon())).ifPresent(coupons_unused::addAll);
            Optional.ofNullable(this.getCoupons(Api_handler.merchant_getHistoryCoupon())).ifPresent(coupons_history::addAll);
            Optional.ofNullable(this.getCoupons(Api_handler.merchant_getUsedCoupon())).ifPresent(coupons_used::addAll);
            Optional.ofNullable(this.getCoupons(Api_handler.merchant_getNotGivenCoupon())).ifPresent(coupons_notgiven::addAll);

            setViewValue(coupons_notgiven, vlist.get(0));
            setViewValue(coupons_unused, vlist.get(1));
            setViewValue(coupons_used, vlist.get(2));
            setViewValue(coupons_history, vlist.get(3));

            tabLayout.getTabAt(0).setText("未發放");
            tabLayout.getTabAt(1).setText("未使用");
            tabLayout.getTabAt(2).setText("已使用");
            tabLayout.getTabAt(3).setText("歷史紀錄");
        }
    }

    private void setView(List<Coupon_entity> coupons, ListView listview, TextView tv_list_view) {
        if (coupons.isEmpty()) {
            listview.setVisibility(View.INVISIBLE);
            tv_list_view.setVisibility(View.VISIBLE);
            tv_list_view.setTextColor(Color.RED);
            tv_list_view.setText(getString(R.string.listView_is_empty));
        } else {
            tv_list_view.setVisibility(View.INVISIBLE);
            listview.setAdapter(new ListCouponAdapter(this, R.layout.content_coupon_layout, coupons));
            listview.setOnItemClickListener((parent, v, position, id) -> {
                Log.d("home", "coupon:" + position);
                Intent intent = new Intent();
                home.this.finish();//關閉activity
                intent.putExtra("coupon", coupons.get(position));
                int currentItem = myViewPager.getCurrentItem();
                intent.putExtra("page_number",currentItem);
                intent.setClass(home.this, coupon_details.class);
                startActivity(intent);
            });
        }
    }

    private void setViewValue(List<Coupon_entity> coupons, View view) {
        listview = (ListView) view.findViewById(R.id.listview_coupon);
        tv_list_view = (TextView) view.findViewById(R.id.textView_show);
        setView(coupons, listview, tv_list_view);
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

    public class myadapter extends PagerAdapter {
        List<View> vlist;
        ViewPager myViewPager;

        public myadapter(List<View> vlist, ViewPager myViewPager) {
            this.vlist = vlist;
            this.myViewPager = myViewPager;
        }

        @Override
        public int getCount() {
            return this.vlist.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            this.myViewPager.addView(this.vlist.get(position));
            return this.vlist.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            myViewPager.removeView((LinearLayout) object);
        }
    }
}