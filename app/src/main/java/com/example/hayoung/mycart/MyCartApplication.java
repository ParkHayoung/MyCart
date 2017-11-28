package com.example.hayoung.mycart;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by hayoung on 2017. 11. 24..
 */

public class MyCartApplication extends Application {

    private static Realm realm;

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("mycart.realm")
                .schemaVersion(1)
                .build();
        realm = Realm.getInstance(config);
    }

    public static Realm getRealm() {
        return realm;
    }
}
