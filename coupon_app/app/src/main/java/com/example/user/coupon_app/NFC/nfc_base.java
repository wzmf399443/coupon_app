package com.example.user.coupon_app.NFC;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import com.example.user.coupon_app.Navigation_baseActivity;

import java.util.Locale;

import static com.example.user.coupon_app.Util.utils.checkNFC;

public abstract class nfc_base extends Navigation_baseActivity implements OutcomingNfcManager.NfcActivity {
    private NfcAdapter nfcAdapter;
    private OutcomingNfcManager outcomingNfccallback;
    public String[] message;
    private String TAG = "NFC";

    @Override
    public String[] getOutcomingMessage() {
        return this.message;
    }

    public void initView() {
        /* Check is NFC supported and enable */
        if (!checkNFC(getApplicationContext())) {
            Toast.makeText(this, "Nfc is not supported on this device", Toast.LENGTH_SHORT).show();
        }else{
            this.nfcAdapter = NfcAdapter.getDefaultAdapter(this);
            // encapsulate sending logic in a separate class
            this.outcomingNfccallback = new OutcomingNfcManager(this);
            this.nfcAdapter.setOnNdefPushCompleteCallback(outcomingNfccallback, this);
            this.nfcAdapter.setNdefPushMessageCallback(outcomingNfccallback, this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Check to see that the Activity started due to an Android Beam
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
            processNFCData(getIntent());
        }
    }

    private void processNFCData(Intent inputIntent) {

        Log.i(TAG, "processNFCData");
        Parcelable[] rawMessages =
                inputIntent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

        if (rawMessages != null && rawMessages.length > 0) {
            NdefMessage[] messages = new NdefMessage[rawMessages.length];
            for (int i = 0; i < rawMessages.length; i++) {
                messages[i] = (NdefMessage) rawMessages[i];
            }

            Log.i(TAG, "message size = " + messages.length);

            // only one message sent during the beam
            NdefMessage msg = (NdefMessage) rawMessages[0];
            // record 0 contains the MIME type, record 1 is the AAR, if present
            String base = new String(msg.getRecords()[0].getPayload());
            String str = String.format(Locale.getDefault(), "Message entries=%d. Base message is %s", rawMessages.length, base);
            Toast.makeText(this,str, Toast.LENGTH_LONG).show();
        }
    }
}
