package com.example.user.coupon_app.customer;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
        myViewPager = (ViewPager) findViewById(R.id.myViewPager);
        tabLayout = (TabLayout) findViewById(R.id.TabLayout);
        setViewPager();;
        tabLayout.setupWithViewPager(myViewPager);
//        setTabLayoutIcon();
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
