package com.example.user.coupon_app.NFC;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;

public class OutcomingNfcManager implements NfcAdapter.CreateNdefMessageCallback,
        NfcAdapter.OnNdefPushCompleteCallback {

    private static final String MIME_TEXT_PLAIN = "text/plain";
    private NfcActivity activity;

    public OutcomingNfcManager(NfcActivity activity) {
        this.activity = activity;
    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        String[] outString = activity.getOutcomingMessage();
        byte[] outBytes = outString.toString().getBytes();
        NdefRecord outRecord = NdefRecord.createMime(MIME_TEXT_PLAIN, outBytes);
        return new NdefMessage(outRecord);
    }

    @Override
    public void onNdefPushComplete(NfcEvent event) {
        // onNdefPushComplete() is called on the Binder thread, so remember to explicitly notify
        // your view on the UI thread
        activity.signalResult();
    }

    /*
     * Callback to be implemented by a Sender activity
     * */
    public interface NfcActivity {
        String[] getOutcomingMessage();
        void signalResult();
    }
}
