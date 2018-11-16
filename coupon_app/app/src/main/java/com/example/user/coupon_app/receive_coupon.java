package com.example.user.coupon_app;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.coupon_app.urlConnector.HttpHandler;

import static com.example.user.coupon_app.Util.utils.checkNFC;

public class receive_coupon extends Navigation_baseActivity {

    private final String MIME_TEXT_PLAIN = "text/plain";
    String Tag = "Receiver Activity";
    private NfcAdapter nfcAdapter;
    private TextView textView_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_coupon);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_application_coupon);//設置ToolBar Title
        setSupportActionBar(toolbar);

        setUpToolBar();//使用父類別的setUpToolBar()，設置ToolBar
        CurrentMenuItem = 1;//目前Navigation項目位置
        NV.getMenu().getItem(CurrentMenuItem).setChecked(true);//設置Navigation目前項目被選取狀態

        /* Check is NFC supported and enable */
        if (!checkNFC(getApplicationContext())) {
            Toast.makeText(this, "Nfc is not supported on this device", Toast.LENGTH_SHORT).show();
            finish();
        }
        this.nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        textView_message = findViewById(R.id.textView_message);
    }


    @Override
    protected void onResume() {
        super.onResume();

        // foreground dispatch should be enabled here, as onResume is the guaranteed place where app
        // is in the foreground
        enableForegroundDispatch(this, this.nfcAdapter);
        receiveMessageFromDevice(getIntent());
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.nfcAdapter.disableForegroundDispatch(this);
    }

    private void receiveMessageFromDevice(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            Parcelable[] parcelables = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

            NdefMessage inNdefMessage = (NdefMessage) parcelables[0];
            NdefRecord[] inNdefRecords = inNdefMessage.getRecords();
            NdefRecord ndefRecord_0 = inNdefRecords[0];

            /* TODO:fix here */
            String text = new String(ndefRecord_0.getPayload());
            this.textView_message.setText(text);
        }
    }

    public void enableForegroundDispatch(AppCompatActivity activity, NfcAdapter adapter) {

        // setting up receiving activity for a foreground dispatch
        // thus if activity is already started it will take precedence over any other activity or app
        // with the same intent filters

        final Intent intent = new Intent(activity.getApplicationContext(), activity.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        final PendingIntent pendingIntent = PendingIntent.getActivity(activity.getApplicationContext(), 0, intent, 0);

        IntentFilter[] filters = new IntentFilter[1];
        String[][] techList = new String[][]{};

        filters[0] = new IntentFilter();
        filters[0].addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
        filters[0].addCategory(Intent.CATEGORY_DEFAULT);
        try {
            filters[0].addDataType(MIME_TEXT_PLAIN);
        } catch (IntentFilter.MalformedMimeTypeException ex) {
            throw new RuntimeException("Check your MIME type");
        }
        adapter.enableForegroundDispatch(activity, pendingIntent, filters, techList);
    }
}
