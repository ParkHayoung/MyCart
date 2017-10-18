package com.example.hayoung.mycart;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.hayoung.mycart.decorator.MyCartItemDecoration;
import com.example.hayoung.mycart.model.CartItem;
import com.example.hayoung.mycart.service.ScreenShotObserveService;
import com.example.hayoung.mycart.util.ServiceTools;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static int REQUEST_STORAGE_PERM = 1;

    public static List<CartItem> items = new ArrayList<>();
    private CartListAdapter cartListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new MyCartItemDecoration(50));

        cartListAdapter = new CartListAdapter();
        cartListAdapter.setItems(items);
        recyclerView.setAdapter(cartListAdapter);

        checkPermission();
    }

    @Override
    protected void onResume() {
        super.onResume();

        cartListAdapter.notifyDataSetChanged();
    }

    public static void addItem(CartItem item) {
        items.add(item);
    }

    private void startScreenShotObserveService() {
        if (!ServiceTools.isServiceRunning(this, ScreenShotObserveService.class)){
            Intent serviceIntent = new Intent(this, ScreenShotObserveService.class);
            startService(serviceIntent);
        }
    }

    private void checkPermission() {
        boolean hasPermission = (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_STORAGE_PERM);
        } else {
            startScreenShotObserveService();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_STORAGE_PERM) {
            boolean allPermissionGranted = true;
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    allPermissionGranted = false;
                }
            }
            if (allPermissionGranted) {
                startScreenShotObserveService();
            }
        }
    }
}
