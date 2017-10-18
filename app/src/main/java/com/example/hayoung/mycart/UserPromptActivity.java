package com.example.hayoung.mycart;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.example.hayoung.mycart.service.ClipboardObserverService;

/**
 * Created by hayoung on 2017. 9. 17..
 */

public class UserPromptActivity extends AppCompatActivity {

    public final static String EXTRA_IMAGE_PATH = "imagePath";

    private String imagePath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imagePath = getIntent().getStringExtra(EXTRA_IMAGE_PATH);
        showAlertDialog();
    }

    private void showAlertDialog() {
        new AlertDialog.Builder(this)
                .setTitle("마이카트 by 하요밍")
                .setMessage("캡쳐된 사진에 연결할 URL 을 클립보드로 복사해주세요.")
                .setCancelable(false)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 확인을 누른 경우, ClipboardManager 를 리스닝하여, URL 을 받아온다.
                        startClipboardObserverService();
                    }
                })
                .setNegativeButton("취소", null)
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        // 다이얼로그가 Dismiss 되면 액티비티를 종료한다.
                        UserPromptActivity.this.finish();
                    }
                })
                .show();
    }

    private void startClipboardObserverService() {
        Intent clipboardServiceIntent = new Intent(this, ClipboardObserverService.class);
        clipboardServiceIntent.putExtra(UserPromptActivity.EXTRA_IMAGE_PATH, imagePath);
        startService(clipboardServiceIntent);
    }
}
