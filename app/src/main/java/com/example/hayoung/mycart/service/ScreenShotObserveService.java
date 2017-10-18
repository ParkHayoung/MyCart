package com.example.hayoung.mycart.service;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.FileObserver;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

import com.example.hayoung.mycart.UserPromptActivity;

import java.io.File;
import java.io.IOException;

/**
 * Created by hayoung on 2017. 9. 17..
 *
 * 스크린샷 디렉토리의 변화를 감지하여 알려주는 서비스.
 */

public class ScreenShotObserveService extends Service {

    private FileObserver fileObserver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private String getScreenShotPath() {
        try {
            return new File(Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DCIM + "/Screenshots")
                    .getCanonicalPath();
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        String path = getScreenShotPath();
        if (path == null) {
            stopSelf();
            return;
        }

        fileObserver = new FileObserver(path, FileObserver.CREATE) {
            @Override
            public void onEvent(final int event, final String path) {
                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    @Override
                    public void run() {
                        Intent userPromptActivityIntent = new Intent(getApplicationContext(), UserPromptActivity.class);
                        userPromptActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                                Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                Intent.FLAG_ACTIVITY_NO_HISTORY);
                        userPromptActivityIntent.putExtra(
                                UserPromptActivity.EXTRA_IMAGE_PATH,
                                getScreenShotPath() + "/" + path);
                        getApplicationContext().startActivity(userPromptActivityIntent);
                    }
                });
            }
        };
        fileObserver.startWatching();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (fileObserver != null) {
            fileObserver.stopWatching();
        }
    }
}
