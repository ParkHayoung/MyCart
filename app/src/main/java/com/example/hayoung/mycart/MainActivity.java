package com.example.hayoung.mycart;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.hayoung.mycart.decorator.MyCartItemDecoration;
import com.example.hayoung.mycart.model.CartItem;
import com.example.hayoung.mycart.service.ScreenShotObserveService;
import com.example.hayoung.mycart.util.ServiceTools;

import java.util.ArrayList;
import java.util.List;

import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements CartListView {

    private static int REQUEST_STORAGE_PERM = 1;

    public static List<CartItem> items = new ArrayList<>();
    private CartListAdapter cartListAdapter;
    private RealmResults<CartItem> realmCartItems;
    private OrderedRealmCollectionChangeListener<RealmResults<CartItem>> realmDataChangeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setTitle("♥내 마음속에 저장♥");

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new MyCartItemDecoration(50));

        cartListAdapter = new CartListAdapter(this);
        cartListAdapter.setItems(items);
        recyclerView.setAdapter(cartListAdapter);

        showContent();
        checkPermission();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cartListAdapter.notifyDataSetChanged();
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

    private void deleteDb() {
        Realm realm = MyCartApplication.getRealm();
        final RealmResults<CartItem> results = realm.where(CartItem.class).findAll();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
                showContent();
            }
        });
    }

    private void showContent() {
        // Build the query looking at all users:
        Realm realm = MyCartApplication.getRealm();
        realmCartItems = realm.where(CartItem.class).findAll();
        realmDataChangeListener = new OrderedRealmCollectionChangeListener<RealmResults<CartItem>>() {
            @Override
            public void onChange(RealmResults<CartItem> cartItems, OrderedCollectionChangeSet changeSet) {
                cartListAdapter.notifyDataSetChanged();
            }
        };
        realmCartItems.addChangeListener(realmDataChangeListener);
        cartListAdapter.setItems(realmCartItems);
        cartListAdapter.notifyDataSetChanged();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        realmCartItems.removeChangeListener(realmDataChangeListener);
    }

    @Override
    public void goToUri(int position) {
        String url = cartListAdapter.getItems().get(position).getDescription();
        Intent viewIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(viewIntent);
    }

    @Override
    public void deleteItem(final int position) {
        new AlertDialog.Builder(this)
                .setTitle("아이템 삭제")
                .setMessage("즐겨찾기한 아이템을 삭제하시겠습니까?")
                .setIcon(R.drawable.heart)
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Realm realm = MyCartApplication.getRealm();
                        realm.beginTransaction();
                        realmCartItems.deleteFromRealm(position);
                        realm.commitTransaction();
                    }
                })
                .setNegativeButton("아니오", null)
                .setCancelable(false)
                .show();
    }
}
