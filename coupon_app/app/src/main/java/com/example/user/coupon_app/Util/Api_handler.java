package com.example.user.coupon_app.Util;

import android.support.v7.app.AppCompatActivity;

import com.example.user.coupon_app.R;
import com.example.user.coupon_app.urlConnector.HttpHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Api_handler extends AppCompatActivity {
    private final String protocol = getString(R.string.api_protocol);
    private final String lable_header = getString(R.string.label_header_token);

    public String get_api_server() {
        return getString(R.string.api_server);
    }

    public String get_url(int id) {
        return protocol + this.get_api_server() + "/" + getString(id);
    }

    public Map<String, String> get_header() {
        return new HashMap<String, String>() {{
            put(lable_header, Identity.getToken());
        }};
    }

    public JSONObject post_to_server(JSONObject post_data, int api, boolean sent_header) throws ExecutionException, InterruptedException {
        Map header = sent_header ? this.get_header() : null;
        return new HttpHandler(this.get_url(api),
                HttpHandler.post, this.get_header(), post_data).execute().get();
    }

    public JSONObject get_from_server(int api, boolean sent_header) throws ExecutionException, InterruptedException {
        Map header = sent_header ? this.get_header() : null;
        return new HttpHandler(this.get_url(api),
                HttpHandler.get, this.get_header(), null).execute().get();
    }

    public JSONObject merchant_register(String account, String password, String name) {
        JSONObject post_data = new JSONObject();

        try {
            post_data.put("account", account);
            post_data.put("pwd", password);
            post_data.put("name", name);
            return post_to_server(post_data, R.string.merchant_register, false);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject merchant_login(String account, String password) {

        JSONObject post_data = new JSONObject();

        try {
            post_data.put("account", account);
            post_data.put("pwd", password);
            return post_to_server(post_data, R.string.merchant_login, false);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject merchant_issueCoupon(String name, int value, int limit, int quantity, Date start_date, Date end_date) {
        JSONObject post_data = new JSONObject();

        try {
            post_data.put("name", name);
            post_data.put("value", value);
            post_data.put("limit", limit);
            return post_to_server(post_data, R.string.merchant_issueCoupon, true);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject merchant_grant(String consumer, int quantity, Date date, String mark, int obtainValue) {
        JSONObject post_data = new JSONObject();

        try {
            post_data.put("consumer", consumer);
            post_data.put("quantity", quantity);
            post_data.put("date", date);
            post_data.put("mark", date);
            post_data.put("obtainValue", obtainValue);
            return post_to_server(post_data, R.string.merchant_grant, true);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject merchant_confirmCouponPay(int consumeValue, Date consumeDate, String couponAddr, String consumer) {
        JSONObject post_data = new JSONObject();

        try {
            post_data.put("consumeValue", consumeValue);
            post_data.put("consumeDate", consumeDate);
            post_data.put("couponAddr", couponAddr);
            post_data.put("consumer", consumer);
            return post_to_server(post_data, R.string.merchant_confirmCouponPay, true);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject merchant_terminateCoupon() {
        try {
            return get_from_server(R.string.merchant_terminateCoupon, true);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject merchant_getUnusedCoupon() {
        try {
            return get_from_server(R.string.merchant_getUnusedCoupon, true);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject merchant_getUsedCoupon() {
        try {
            return get_from_server(R.string.merchant_getUsedCoupon, true);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject merchant_getHistoryCoupon() {
        try {
            return get_from_server(R.string.merchant_getHistoryCoupon, true);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject consumer_register(String account, String password, String name) {
        JSONObject post_data = new JSONObject();

        try {
            post_data.put("account", account);
            post_data.put("password", password);
            post_data.put("name", name);
            return post_to_server(post_data, R.string.consumer_register, false);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject consumer_login(String account, String password) {
        JSONObject post_data = new JSONObject();

        try {
            post_data.put("account", account);
            post_data.put("password", password);
            return post_to_server(post_data, R.string.consumer_login, false);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public JSONObject consumer_transfer(String receive, String coupon) {
        JSONObject post_data = new JSONObject();

        try {
            post_data.put("receive", receive);
            post_data.put("coupon", coupon);
            return post_to_server(post_data, R.string.consumer_transfer, true);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject consumer_getCoupons() {
        try {
            return get_from_server(R.string.consumer_getCoupons, true);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
