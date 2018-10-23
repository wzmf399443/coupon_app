package com.example.user.coupon_app;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Navigation_BaseActivity {

//    private DrawerLayout drawerLayout;
//    private NavigationView navigation_view;
//    private Toolbar toolbar;
//
//    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    setUpToolBar();//使用父類別的setUpToolBar()，設置ToolBar
//    CurrentMenuItem = 0;//目前Navigation項目位置
    NV.getMenu().getItem(CurrentMenuItem).setChecked(true);//設置Navigation目前項目被選取狀態
//
//        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
//        navigation_view = (NavigationView) findViewById(R.id.navigation_view);
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
////
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
//        drawerLayout.addDrawerListener(toggle);
//        toggle.syncState();
//
//        // 為navigatin_view設置點擊事件
//        navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//                // 點選時收起選單
//                drawerLayout.closeDrawer(GravityCompat.START);
//
//                // 取得選項id
//                int id = item.getItemId();
//
//                // 依照id判斷點了哪個項目並做相應事件
//                if (id == R.id.action_home) {
//                    // 按下「首頁」要做的事
//                    Toast.makeText(MainActivity.this, "首頁", Toast.LENGTH_SHORT).show();
//                    return true;
//                }
//                else if (id == R.id.action_application_coupon) {
//                    // 按下「使用說明」要做的事
//                    Toast.makeText(MainActivity.this, "申請coupon", Toast.LENGTH_SHORT).show();
//                    return true;
//                }
//                else if (id == R.id.action_application_pay) {
//                    // 按下「使用說明」要做的事
//                    Toast.makeText(MainActivity.this, "申請支付", Toast.LENGTH_SHORT).show();
//                    return true;
//                }
//                else if (id == R.id.action_wallet) {
//                    // 按下「使用說明」要做的事
//                    Toast.makeText(MainActivity.this, "查看錢包", Toast.LENGTH_SHORT).show();
//                    return true;
//                }
//                else if (id == R.id.action_gift) {
//                    // 按下「使用說明」要做的事
//                    Toast.makeText(MainActivity.this, "轉贈", Toast.LENGTH_SHORT).show();
//                    return true;
//                }
//                // 略..
//
//                return false;
//            }
//        });
    }

}
