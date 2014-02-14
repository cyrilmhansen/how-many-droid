package fr.softwaresemantics.howmanydroid;

import android.content.Context;

import fr.softwaresemantics.howmanydroid.ui.mjview.MJViewCache;

/**
 * Created by cmh on 09/02/14.
 */
public class HMDApplication extends android.app.Application {

    private static HMDApplication instance;

    public HMDApplication() {
        instance = this;

       //
    }

    @Override
    public void onCreate() {
        super.onCreate();

        MJViewCache.init(10);
    }

    public static Context getContext() {
        return instance;
    }

}
