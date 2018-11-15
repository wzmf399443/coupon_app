package com.example.user.coupon_app.customer;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.example.user.coupon_app.Navigation_baseActivity;
import com.example.user.coupon_app.R;

import java.util.ArrayList;
import java.util.List;

public class customer_tablayout extends FragmentActivity {


    private ViewPager myViewPager;
    private TabLayout tabLayout;
    private int[] IconResID = {R.drawable.consumer,R.drawable.home,R.drawable.coupons};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_tablayout);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle(R.string.title_home); //設置ToolBar Title
//        setSupportActionBar(toolbar);

//        setUpToolBar(); //使用父類別的setUpToolBar()，設置ToolBar
//        CurrentMenuItem = 0; //目前Navigation項目位置
//        NV.getMenu().getItem(CurrentMenuItem).setChecked(true); //設置Navigation目前項目被選取狀態

        myViewPager = (ViewPager) findViewById(R.id.myViewPager);
        tabLayout = (TabLayout) findViewById(R.id.TabLayout);
////        myViewPager.setAdapter(new myadpter());
        myViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
//        tabLayout.setupWithViewPager(myViewPager);
//        tabLayout.getTabAt(0).setText("可使用");
//        tabLayout.getTabAt(1).setText("已使用");


//        setTabLayoutIcon();
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch(pos) {

                case 0: return coupon_list_available.newInstance("FirstFragment, Instance 1");
                case 1: return coupon_list_used.newInstance("SecondFragment, Instance 1");
                default: return coupon_list_available.newInstance("FirstFragment, Default");
            }
        }

        @Override
        public int getCount() {
            return 1;
        }
    }

    public class myadpter extends PagerAdapter{
        LayoutInflater layoutInflater;
        int[] layouts={R.layout.activity_home,R.layout.activity_coupon_list_available};
        @Override
        public int getCount(){
            return layouts.length;
        }
        @Override
        public boolean isViewFromObject(View view,Object object){
            return (view==(LinearLayout)object);
        }

        @Override
        public Object instantiateItem(ViewGroup container,int position){
            layoutInflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View one =layoutInflater.inflate(R.layout.activity_coupon_list_available,container,false);
            View two =layoutInflater.inflate(R.layout.activity_coupon_list_used,container,false);
            View viewarr[]={one,two};
            container.addView(viewarr[position]);
            return  viewarr[position];
        }

        @Override
        public void destroyItem(ViewGroup container,int position ,Object object){
            container.removeView((LinearLayout)object);
        }
    }
}
