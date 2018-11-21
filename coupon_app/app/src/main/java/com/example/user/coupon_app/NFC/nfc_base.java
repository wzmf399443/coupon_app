package com.example.user.coupon_app.NFC;

import android.nfc.NfcAdapter;
import android.widget.Toast;

import com.example.user.coupon_app.Navigation_baseActivity;

import static com.example.user.coupon_app.Util.utils.checkNFC;

public abstract class nfc_base extends Navigation_baseActivity implements OutcomingNfcManager.NfcActivity {
    private NfcAdapter nfcAdapter;
    private OutcomingNfcManager outcomingNfccallback;
    public String[] message;

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
}
