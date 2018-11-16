package com.example.user.coupon_app;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.coupon_app.NFC.OutcomingNfcManager;
import com.example.user.coupon_app.Util.Identity;

import static com.example.user.coupon_app.Util.utils.checkNFC;

public class send_coupon extends Navigation_baseActivity implements OutcomingNfcManager.NfcActivity {
    private NfcAdapter nfcAdapter;
    private OutcomingNfcManager outcomingNfccallback;
    private ConstraintLayout accept_view, sending_view, coupon_details_view;
    private ConstraintLayout coupon_view;
    private String[] message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_pay);
        initView();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
    }

    @Override
    public String[] getOutcomingMessage() {
        return this.message;
    }

    @Override
    public void signalResult() {
        // this will be triggered when NFC message is sent to a device.
        // should be triggered on UI thread. We specify it explicitly
        // cause onNdefPushComplete is called from the Binder thread
        runOnUiThread(() ->
                Toast.makeText(send_coupon.this, "send complete", Toast.LENGTH_SHORT).show());
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_application_pay);//設置ToolBar Title
        setSupportActionBar(toolbar);

        setUpToolBar();//使用父類別的setUpToolBar()，設置ToolBar
        CurrentMenuItem = 2;//目前Navigation項目位置
        NV.getMenu().getItem(CurrentMenuItem).setChecked(true);//設置Navigation目前項目被選取狀態

        accept_view = findViewById(R.id.accept_coupon);
        sending_view = findViewById(R.id.sending_content);
        coupon_details_view = findViewById(R.id.coupon_details);
        coupon_view = findViewById(R.id.coupon_layout);

        accept_view.setVisibility(View.INVISIBLE);
        sending_view.setVisibility(View.INVISIBLE);
        coupon_details_view.setVisibility(View.INVISIBLE);
        coupon_view.setVisibility(View.INVISIBLE);

        /* Check is NFC supported and enable */
        if (!checkNFC(getApplicationContext())) {
            Toast.makeText(this, "Nfc is not supported on this device", Toast.LENGTH_SHORT).show();
        } else {
            this.nfcAdapter = NfcAdapter.getDefaultAdapter(this);
            // encapsulate sending logic in a separate class
            this.outcomingNfccallback = new OutcomingNfcManager(this);
            this.nfcAdapter.setOnNdefPushCompleteCallback(outcomingNfccallback, this);
            this.nfcAdapter.setNdefPushMessageCallback(outcomingNfccallback, this);
        }

        String method = getIntent().getStringExtra("method");
        switch(method){
            case "coupon_send":
                this.coupon_send();
                break;
            case "coupon_pay":
                this.coupon_pay();
                break;
            case "coupon_obtain_coupon":
                this.coupon_obtain_coupon();
                break;
            case "coupon_receive":
                this.coupon_receive();
                break;
            case "coupon_accept":
                this.coupon_accept();
                break;
            case "issue_coupon":
                this.issue_coupon();
                break;
        }
    }

    private void coupon_send() {
        Intent intent = getIntent();
        String[] message = {intent.getStringExtra("coupon")};
        this.message = message;
        this.set_sending_message_text(message[0]);
        sending_view.setVisibility(View.VISIBLE);
    }

    private void coupon_pay() {
        Intent intent = getIntent();
        String[] message = {intent.getStringExtra("coupon"), Identity.getToken()};
        this.message = message;
        this.set_sending_message_text(message[0]);
        sending_view.setVisibility(View.VISIBLE);
    }

    private void coupon_obtain_coupon() {
        String[] message = {Identity.getToken()};
        this.message = message;
        TextView textView_sending_coupon_name = sending_view.findViewById(R.id.textView_sending_coupon_name);
        textView_sending_coupon_name.setText("getting coupon...");
        sending_view.setVisibility(View.VISIBLE);
    }

    private void coupon_receive() {
        this.set_accepting_content();
    }

    private void coupon_accept() {
        this.set_accepting_content();
    }

    private void issue_coupon() {
        Coupon_entity entity = (Coupon_entity) getIntent().getSerializableExtra("coupon");
        this.set_show_coupon_message_content(entity);
    }

    private void set_sending_message_text(String coupon_name) {
        /* show help message */
        TextView textView_sending_coupon_name = sending_view.findViewById(R.id.textView_sending_coupon_name);
        textView_sending_coupon_name.setText("sending coupon" + coupon_name);
    }

    private void set_accepting_content() {
        /* waiting progress bar */
        /* help message */
        accept_view.setVisibility(View.VISIBLE);
    }

    private void set_show_coupon_message_content(Coupon_entity entity) {
        /* show entity here */
        /* set consume value */
        coupon_details_view.setVisibility(View.VISIBLE);
        coupon_view.setVisibility(View.VISIBLE);
        this.set_coupon_layout(entity);

        TextView textView_couponAddress, textView_limit, textView_obtainValue, textView_status,
                textView_startDate, textView_obtainDate, textView_consumeDate;

        /* coupon details view */
        textView_couponAddress = coupon_details_view.findViewById(R.id.textView_couponAddress);
        textView_limit = coupon_details_view.findViewById(R.id.textView_limit);
        textView_obtainValue = coupon_details_view.findViewById(R.id.textView_obtainValue);
        textView_status = coupon_details_view.findViewById(R.id.textView_status);
        textView_startDate = coupon_details_view.findViewById(R.id.textView_startDate);
        textView_obtainDate = coupon_details_view.findViewById(R.id.textView_obtainDate);
        textView_consumeDate = coupon_details_view.findViewById(R.id.textView_consumeDate);

        textView_couponAddress.setText(entity.getCouponAddress());
        textView_limit.setText(entity.getLimit());
        textView_obtainValue.setText(entity.getObtainValue());
        textView_status.setText(entity.getStatus());
        textView_startDate.setText(entity.getStartDate());
        textView_obtainDate.setText(entity.getObtainDate());
        textView_consumeDate.setText(entity.getConsumeDate());
    }

    private void set_coupon_layout(Coupon_entity entity) {

        TextView textView_name = coupon_view.findViewById(R.id.textview_coupon_name);
        TextView textView_expire_date = coupon_view.findViewById(R.id.textview_expire_date);
        TextView textView_merchant = coupon_view.findViewById(R.id.textview_merchant);
        TextView textView_value = coupon_view.findViewById(R.id.textview_value);
        TextView textView_consume_value = coupon_view.findViewById(R.id.textview_consumerValue);

        ImageView imageView = coupon_view.findViewById(R.id.imageview_coupon);

        textView_name.setText(entity.getCoupon_name());
        textView_expire_date.setText(entity.getExpire_date());
        textView_merchant.setText(entity.getCoupon_merchant());
        textView_value.setText(entity.getCoupon_value());
        textView_consume_value.setText(String.valueOf(entity.getCoupon_consumerValue()));

        /* TODO:put images here */
        imageView.setImageResource(R.drawable.c_logo);
    }
}
