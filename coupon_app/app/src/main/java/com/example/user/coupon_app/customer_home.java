package com.example.user.coupon_app;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.coupon_app.Util.Api_handler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

public class customer_home extends  Navigation_baseActivity {
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_home); //設置ToolBar Title
        setSupportActionBar(toolbar);

        setUpToolBar(); //使用父類別的setUpToolBar()，設置ToolBar
        CurrentMenuItem = 0; //目前Navigation項目位置
        NV.getMenu().getItem(CurrentMenuItem).setChecked(true); //設置Navigation目前項目被選取狀態

        listview = findViewById(R.id.listview_coupon);
        try {

            JSONObject list_of_coupons = Api_handler.consumer_getCoupons();

            if (list_of_coupons == null ||
                    !list_of_coupons.getBoolean(getString(R.string.response_success))) {
                /* TODO:Remove this after api server is finished */
                list_of_coupons = new JSONObject();
                Date date = new Date();

                list_of_coupons.put("success", true);
                list_of_coupons.put("message", "good");
                JSONArray data = new JSONArray();
                JSONObject coupon = new JSONObject() {{
                    put("merchant", "NUTC");
                    put("couponName", "good");
                    put("value", 100);
                    put("consumerValue", 200);
                    put("limit", 10);
                    put("obtainValue", 20);
                    put("status", 1);
                    put("startDate", date.toString());
                    put("obtainDate", date.toString());
                    put("endDate", date.toString());
                }};
                data.put(coupon);
                list_of_coupons.put("data", data);
            }

            JSONArray data = list_of_coupons.getJSONArray(getString(R.string.response_data));
            //ArrayList<Coupon_entity> coupons = new Gson().fromJson(data.toString(), ArrayList.class);
            Type type = new TypeToken<List<Coupon_entity>>() {
            }.getType();
            List<Coupon_entity> coupons = new Gson().fromJson(data.toString(), type);
            listview.setAdapter(new ListCouponAdapter(this, R.layout.coupon_layout, coupons));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(customer_home.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

}
