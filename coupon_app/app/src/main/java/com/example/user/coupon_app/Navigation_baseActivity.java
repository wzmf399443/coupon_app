package com.example.user.coupon_app;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.annotation.LayoutRes;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.user.coupon_app.Util.Identity;

/**
 * Created by user on 2018/10/22.
 */

public class Navigation_baseActivity extends AppCompatActivity {
    private DrawerLayout DL;
    private FrameLayout FL;
    protected NavigationView NV;
    protected Toolbar toolbar;
    protected int CurrentMenuItem = 0;//紀錄目前User位於哪一個項目

    @Override
    public void setContentView (@LayoutRes int layoutresID){
        if (Identity.getIdentity().equals(getString(R.string.id_customer))){
            DL = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_main, null);
            FL = (FrameLayout) DL.findViewById(R.id.content_frame);
            NV = (NavigationView)DL.findViewById(R.id.navigation_view);
            getLayoutInflater().inflate(layoutresID, FL, true);
            super.setContentView(DL);
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setUpNavigation_cus();
        }
        if (Identity.getIdentity().equals(getString(R.string.id_store))){
            DL = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_store_drawer, null);
            FL = (FrameLayout) DL.findViewById(R.id.content_frame_store);
            NV = (NavigationView)DL.findViewById(R.id.navigation_store_view);
            getLayoutInflater().inflate(layoutresID, FL, true);
            super.setContentView(DL);
            toolbar = (Toolbar) findViewById(R.id.toolbar_store);
            setUpNavigation_store();
        }
    }

    private void setUpNavigation_cus() {
        // Set navigation item selected listener
        NV.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if (!(menuItem == NV.getMenu().getItem(CurrentMenuItem))) {//判斷使者者是否點擊當前畫面的項目，若不是，根據所按的項目做出分別的動作
                    switch (menuItem.getItemId()) {
                        case R.id.action_home:
                            Intent intent = new Intent();
                            intent.setClass(Navigation_baseActivity.this, customer_home.class);
                            startActivity(intent);
                            overridePendingTransition(0, 0);
                            finish();
                            break;
                        case R.id.action_application_coupon:
                            Intent intent2 = new Intent();
                            intent2.setClass(Navigation_baseActivity.this, receive_coupon.class);
                            startActivity(intent2);
                            overridePendingTransition(0, 0);
                            finish();
                            break;
                        case R.id.action_application_pay:
                            Intent intent3 = new Intent();
                            intent3.setClass(Navigation_baseActivity.this, send_coupon.class);
                            startActivity(intent3);
                            overridePendingTransition(0, 0);
                            finish();
                            break;
                        case R.id.action_wallet:
                            Intent intent4 = new Intent();
                            intent4.setClass(Navigation_baseActivity.this, customer_wallet.class);
                            startActivity(intent4);
                            overridePendingTransition(0, 0);
                            finish();
                            break;
                        case R.id.action_gift:
                            Intent intent5 = new Intent();
                            intent5.setClass(Navigation_baseActivity.this, customer_gift.class);
                            startActivity(intent5);
                            overridePendingTransition(0, 0);
                            finish();
                            break;
                        case R.id.test_scan:
                            Intent intent6 = new Intent();
                            intent6.setClass(Navigation_baseActivity.this, test_coupon.class);
                            startActivity(intent6);
                            overridePendingTransition(0, 0);
                            finish();
                            break;
                        case R.id.test_create:
                            Intent intent7 = new Intent();
                            intent7.setClass(Navigation_baseActivity.this, test_create_qrcode.class);
                            startActivity(intent7);
                            overridePendingTransition(0, 0);
                            finish();
                            break;
                    }
                } else {//點擊當前項目時，收起Navigation
                    DL.closeDrawer(GravityCompat.START);
                }
                return false;
            }
        });
    }

//    public void setUpToolBar() {//設置ToolBar
//        setSupportActionBar(toolbar);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DL.openDrawer(GravityCompat.START);
//            }
//        });
//        //設定當使用者點擊ToolBar中的Navigation Icon時，Icon會隨著轉動
//        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, DL, toolbar, R.string.drawer_open, R.string.drawer_close) {
//            @Override
//            public void onDrawerClosed(View drawerView) {
//                super.onDrawerClosed(drawerView);
//            }
//
//            @Override
//            public void onDrawerOpened(View drawerView) {
//                super.onDrawerOpened(drawerView);
//            }
//        };
//        DL.addDrawerListener(actionBarDrawerToggle);
//        actionBarDrawerToggle.syncState();
//    }
    private void setUpNavigation_store() {
        // Set navigation item selected listener
        NV.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if (!(menuItem == NV.getMenu().getItem(CurrentMenuItem))) {//判斷使者者是否點擊當前畫面的項目，若不是，根據所按的項目做出分別的動作
                    switch (menuItem.getItemId()) {
                        case R.id.action_home:
                            Intent intent = new Intent();
                            intent.setClass(Navigation_baseActivity.this, store_home.class);
                            startActivity(intent);
                            overridePendingTransition(0, 0);
                            finish();
                            break;
                        case R.id.action_issue_coupon:
                            Intent intent2 = new Intent();
                            intent2.setClass(Navigation_baseActivity.this, store_home.class);
                            startActivity(intent2);
                            overridePendingTransition(0, 0);
                            finish();
                            break;
                        case R.id.action_grant_coupon:
                            Intent intent3 = new Intent();
                            intent3.setClass(Navigation_baseActivity.this, send_coupon.class);
                            startActivity(intent3);
                            overridePendingTransition(0, 0);
                            finish();
                            break;
                        case R.id.accept_coupon:
                            Intent intent4 = new Intent();
                            intent4.setClass(Navigation_baseActivity.this, receive_coupon.class);
                            startActivity(intent4);
                            overridePendingTransition(0, 0);
                            finish();
                            break;
                        case R.id.action_stop_grant:
                            Intent intent5 = new Intent();
                            intent5.setClass(Navigation_baseActivity.this, customer_gift.class);
                            startActivity(intent5);
                            overridePendingTransition(0, 0);
                            finish();
                            break;
//                        case R.id.test_scan:
//                            Intent intent6 = new Intent();
//                            intent6.setClass(Navigation_baseActivity.this, test_coupon.class);
//                            startActivity(intent6);
//                            overridePendingTransition(0, 0);
//                            finish();
//                            break;
//                        case R.id.test_create:
//                            Intent intent7 = new Intent();
//                            intent7.setClass(Navigation_baseActivity.this, test_create_qrcode.class);
//                            startActivity(intent7);
//                            overridePendingTransition(0, 0);
//                            finish();
//                            break;
                    }
                } else {//點擊當前項目時，收起Navigation
                    DL.closeDrawer(GravityCompat.START);
                }
                return false;
            }
        });
    }

    public void setUpToolBar() {//設置ToolBar
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DL.openDrawer(GravityCompat.START);
            }
        });
        //設定當使用者點擊ToolBar中的Navigation Icon時，Icon會隨著轉動
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, DL, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        DL.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }
}

