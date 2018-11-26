package com.example.user.coupon_app.store;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.coupon_app.Navigation_baseActivity;
import com.example.user.coupon_app.R;
import com.example.user.coupon_app.Util.Api_handler;
import com.example.user.coupon_app.Util.utils;
import com.example.user.coupon_app.home;

import org.json.JSONObject;

import java.util.Calendar;

public class store_issue_coupon extends Navigation_baseActivity {
    TextView tv_status;
    EditText edit_name;
    EditText edit_value;
    EditText edit_limit;
    EditText edit_quantity;
    EditText edit_start;
    EditText edit_end;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_issue_coupon);

        Toolbar toolbar = findViewById(R.id.toolbar_store);
        toolbar.setTitle(R.string.title_issue_coupon);//設置ToolBar Title
        setSupportActionBar(toolbar);

        setUpToolBar();//使用父類別的setUpToolBar()，設置ToolBar
        CurrentMenuItem = 1;//目前Navigation項目位置
        NV.getMenu().getItem(CurrentMenuItem).setChecked(true);//設置Navigation目前項目被選取狀態

        tv_status=findViewById(R.id.textView_status);
        edit_name = findViewById(R.id.editText_name);
        edit_value = findViewById(R.id.editText_value);
        edit_limit = findViewById(R.id.editText_limit);
        edit_quantity = findViewById(R.id.editText_quantity);
        edit_start = findViewById(R.id.editText_start);
        edit_end = findViewById(R.id.editText_end);
        edit_start.setInputType(InputType.TYPE_NULL);
        edit_end.setInputType(InputType.TYPE_NULL);
        edit_start.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    showDatePickerDialog(edit_start);
                }
            }
        });
        edit_start.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDatePickerDialog(edit_start);
            }
        });
        edit_end.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    showDatePickerDialog(edit_end);
                }
            }
        });
        edit_end.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDatePickerDialog(edit_end);
            }
        });
    }
    public void showDatePickerDialog(EditText edit_text){
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(store_issue_coupon.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                edit_text.setText(year+"/"+(monthOfYear+1)+"/"+dayOfMonth);
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();

    }

    public void issue_coupon(View view) {

        try {
            JSONObject resp = Api_handler.merchant_issueCoupon(
                    tv_status.getText().toString(),
                    Integer.valueOf(edit_value.getText().toString()),
                    Integer.valueOf(edit_limit.getText().toString()),
                    Integer.valueOf(edit_quantity.getText().toString()),
                    edit_start.getText().toString(),
                    edit_end.getText().toString()
            );
            if (resp != null && resp.getBoolean(getString(R.string.response_success))) {
                Toast.makeText(this,getString(R.string.issue_coupon_success),Toast.LENGTH_LONG);
                Intent intent = new Intent();
                intent.setClass(this, home.class);
                startActivity(intent);
            }else{
                utils.set_text_error_message(view,tv_status,resp.optString("message"));
            }
        }catch (Exception e){
            e.printStackTrace();
            utils.set_text_error_message(view,tv_status,getString(R.string.issue_coupon_failed));
        }
    }
}
