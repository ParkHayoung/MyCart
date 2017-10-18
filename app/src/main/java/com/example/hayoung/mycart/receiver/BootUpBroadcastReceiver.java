package com.example.hayoung.mycart.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.hayoung.mycart.service.ScreenShotObserveService;

/**
 * Created by hayoung on 2017. 9. 17..
 */

public class BootUpBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            return;
        }
        Intent serviceIntent = new Intent(context, ScreenShotObserveService.class);
        context.startService(serviceIntent);
    }
}
