package com.example.hayoung.mycart.service;

import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.hayoung.mycart.MainActivity;
import com.example.hayoung.mycart.UserPromptActivity;
import com.example.hayoung.mycart.model.CartItem;

/**
 * Created by hayoung on 2017. 9. 17..
 */

public class ClipboardObserverService extends Service {
    private ClipboardManager clipboardManager;
    private ClipboardManager.OnPrimaryClipChangedListener primaryClipChangedListener;
    private String imagePath;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        imagePath = intent.getStringExtra(UserPromptActivity.EXTRA_IMAGE_PATH);
//        return super.onStartCommand(intent, flags, startId);
        return START_REDELIVER_INTENT;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        clipboardManager = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
        primaryClipChangedListener = new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {
                ClipData clipData = clipboardManager.getPrimaryClip();
                try {
                    String text = clipData.getItemAt(0).getText().toString();
                    Uri uri = Uri.parse(text);
                    MainActivity.addItem(new CartItem(imagePath, uri.toString()));
                    Toast.makeText(getApplicationContext(), "마이카트에 저장되었습니다.", Toast.LENGTH_SHORT).show();

                    // 서비스 종료
                    stopSelf();
                } catch (Exception e) {
                    // ignore exception
                    Toast.makeText(getApplicationContext(), "복사한 텍스트가 유효한 URL 형식이 아닙니다.", Toast.LENGTH_SHORT).show();
                }
            }
        };
        clipboardManager.addPrimaryClipChangedListener(primaryClipChangedListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        clipboardManager.removePrimaryClipChangedListener(primaryClipChangedListener);
    }
}
