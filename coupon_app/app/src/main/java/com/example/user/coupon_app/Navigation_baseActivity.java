package com.example.user.coupon_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.annotation.LayoutRes;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.user.coupon_app.Util.Identity;
import com.example.user.coupon_app.customer.customer_gift;
import com.example.user.coupon_app.customer.customer_tablayout;
import com.example.user.coupon_app.customer.customer_wallet;
import com.example.user.coupon_app.store.store_issue_coupon;
import com.example.user.coupon_app.store.store_stop_grant;

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
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {//捕捉返回鍵
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            ConfirmExit();//按返回鍵，則執行退出確認
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    public void ConfirmExit(){//退出確認
        AlertDialog.Builder ad=new AlertDialog.Builder(Navigation_baseActivity.this);
        ad.setTitle("登出");
        ad.setMessage("確定要登出嗎?");
        ad.setPositiveButton("是", new DialogInterface.OnClickListener() {//退出按鈕
            public void onClick(DialogInterface dialog, int i) {
                // TODO Auto-generated method stub
                Navigation_baseActivity.this.finish();//關閉activity
                Intent intent = new Intent();
                intent.setClass(Navigation_baseActivity.this, login_page.class);
                startActivity(intent);
            }
        });
        ad.setNegativeButton("否",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int i) {
                //不退出不用執行任何操作
            }
        });
        ad.show();//顯示對話框
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
                            intent.setClass(Navigation_baseActivity.this, home.class);
                            startActivity(intent);
                            overridePendingTransition(0, 0);
                            finish();
                            break;
                        case R.id.action_application_coupon:
                            Intent intent2 = new Intent();
                            intent2.setClass(Navigation_baseActivity.this, sending_coupon.class);
                            intent2.putExtra("method","coupon_obtain_coupon");
                            startActivity(intent2);
                            //overridePendingTransition(0, 0);
                            //finish();
                            break;
                        case R.id.action_gift:
                            Intent intent5 = new Intent();
                            intent5.setClass(Navigation_baseActivity.this, sending_coupon.class);
                            intent5.putExtra("method","coupon_receive");
                            startActivity(intent5);
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

    private void setUpNavigation_store() {
        // Set navigation item selected listener
        NV.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if (!(menuItem == NV.getMenu().getItem(CurrentMenuItem))) {//判斷使者者是否點擊當前畫面的項目，若不是，根據所按的項目做出分別的動作
                    switch (menuItem.getItemId()) {
                        case R.id.action_home:
                            Intent intent = new Intent();
                            intent.setClass(Navigation_baseActivity.this, home.class);
                            startActivity(intent);
                            overridePendingTransition(0, 0);
                            finish();
                            break;
                        case R.id.action_issue_coupon:
                            Intent intent2 = new Intent();
                            intent2.setClass(Navigation_baseActivity.this, store_issue_coupon.class);
                            startActivity(intent2);
                            overridePendingTransition(0, 0);
                            finish();
                            break;
                        case R.id.accept_coupon:
                            Intent intent4 = new Intent();
                            intent4.setClass(Navigation_baseActivity.this, accept_coupon.class);
                            intent4.putExtra("method","coupon_accept");
                            startActivity(intent4);
                            overridePendingTransition(0, 0);
                            finish();
                            break;
                        case R.id.action_stop_grant:
                            Intent intent5 = new Intent();
                            intent5.setClass(Navigation_baseActivity.this, store_stop_grant.class);
                            startActivity(intent5);
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

