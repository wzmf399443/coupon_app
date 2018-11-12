package com.example.user.coupon_app.customer;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
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

public class customer_tablayout extends Navigation_baseActivity {


    private ViewPager myViewPager;
    private TabLayout tabLayout;
    private int[] IconResID = {R.drawable.consumer,R.drawable.home,R.drawable.coupons};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_tablayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_home); //設置ToolBar Title
        setSupportActionBar(toolbar);

        setUpToolBar(); //使用父類別的setUpToolBar()，設置ToolBar
        CurrentMenuItem = 0; //目前Navigation項目位置
        NV.getMenu().getItem(CurrentMenuItem).setChecked(true); //設置Navigation目前項目被選取狀態

        myViewPager = (ViewPager) findViewById(R.id.myViewPager);
        tabLayout = (TabLayout) findViewById(R.id.TabLayout);
        myViewPager.setAdapter(new myadpter());
//        setViewPager();;
        tabLayout.setupWithViewPager(myViewPager);
        tabLayout.getTabAt(0).setText("可使用");
        tabLayout.getTabAt(1).setText("已使用");


//        setTabLayoutIcon();
    }

    public class myadpter extends PagerAdapter{
        LayoutInflater layoutInflater;
        int[] layouts={R.layout.content_home,R.layout.activity_coupon_list_available};
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
            View one =layoutInflater.inflate(R.layout.content_home,container,false);
            View two =layoutInflater.inflate(R.layout.activity_coupon_list_available,container,false);
            View viewarr[]={one,two};
            container.addView(viewarr[position]);
            return  viewarr[position];
        }

        @Override
        public void destroyItem(ViewGroup container,int position ,Object object){
            container.removeView((LinearLayout)object);
        }
    }
//    public void setTabLayoutIcon(){
//        for(int i =0; i < IconResID.length;i++){
//            tabLayout.getTabAt(i).setIcon(IconResID[i]);
//        }

//    }
    private void setViewPager(){
        coupon_list_available myFragment1 = new coupon_list_available();
        coupon_list_used myFragment2 = new coupon_list_used();
        List<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(myFragment1);
        fragmentList.add(myFragment2);
        fragmentAdapter myFragmentAdapter = new fragmentAdapter(getSupportFragmentManager(), fragmentList);
        myViewPager.setAdapter(myFragmentAdapter);
    }

}
