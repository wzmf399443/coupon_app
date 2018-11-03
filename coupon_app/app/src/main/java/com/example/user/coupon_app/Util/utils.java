package com.example.user.coupon_app.Util;

import android.content.Context;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;

public class utils {

    public static boolean checkNFC(Context context) {
        NfcManager manager = (NfcManager) context.getSystemService(Context.NFC_SERVICE);
        assert manager != null;
        NfcAdapter adapter = manager.getDefaultAdapter();
        return adapter != null && adapter.isEnabled();
    }
}
