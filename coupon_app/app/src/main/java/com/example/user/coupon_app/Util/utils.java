package com.example.user.coupon_app.Util;

import android.content.Context;
import android.graphics.Color;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.view.View;
import android.widget.TextView;

public class utils {

    public static boolean checkNFC(Context context) {
        NfcManager manager = (NfcManager) context.getSystemService(Context.NFC_SERVICE);
        assert manager != null;
        NfcAdapter adapter = manager.getDefaultAdapter();
        return adapter != null && adapter.isEnabled();
    }

    public static void set_text_error_message(View view,TextView tv,String message){
        tv.setText(message);
        tv.setTextColor(Color.RED);
        tv.setVisibility(view.VISIBLE);
    }
}
