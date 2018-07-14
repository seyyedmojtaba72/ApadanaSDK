package com.github.seyyedmojtaba72.apadana_sdk_android;

import android.content.Context;
import android.content.pm.PackageManager;

import com.github.seyyedmojtaba72.android_utils.DateManager;
import com.github.seyyedmojtaba72.android_utils.PreferencesManager;
import com.github.seyyedmojtaba72.apadana_sdk_android.info.Constants;
import com.github.seyyedmojtaba72.apadana_sdk_android.info.EndPoints;

import java.util.TimeZone;

/**
 * Created by seyyedmojtaba72 on 1/28/18.
 */

public class Apadana {

    private final Context mContext;
    private Constants mConstants;

    private int mVersionNumber = 0;
    private String mVersionName = "";

    public Apadana(Context context) {
        this.mContext = context;
        this.mConstants = new Constants(context);

        try {
            mVersionNumber = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
            mVersionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Apadana startInit(Context context) {
        return new Apadana(context);
    }

    ////////

    public Apadana setHomeUrl(String url) {
        PreferencesManager.putString(mContext, "apadana_home_url", url);

        return this;
    }

    public Apadana setSystemUrl(String url) {
        PreferencesManager.putString(mContext, "apadana_system_url", url);

        return this;
    }

    public Apadana setSecretKey(String key) {
        PreferencesManager.putString(mContext, "apadana_secret_key", key);

        return this;
    }

    ////////

    public String getHomeUrl(){
        return getHomeUrl(mContext);
    }

    public String getSystemUrl(){
        return getSystemUrl(mContext);
    }

    public String getSecretKey(){
        return getSecretKey(mContext);
    }

    public static String getHomeUrl(Context context) {
        return PreferencesManager.getString(context, "apadana_home_url", "");
    }

    public static String getSystemUrl(Context context) {
        return PreferencesManager.getString(context, "apadana_system_url", "");
    }

    public static String getSecretKey(Context context) {
        return PreferencesManager.getString(context, "apadana_secret_key", "");
    }

    ///////

    public void init() {
        this.mConstants.fetchConstants();
    }

    public boolean isInMaintenace() {
        long maintenance_time = PreferencesManager.getLong(mContext, Constants.APADANA_KEY_MAINTENANCE_TIME, 0) * 1000;
        long now_time = DateManager.getNowTimestamp(TimeZone.getTimeZone(PreferencesManager.getString(mContext, Constants.APADANA_KEY_TIMEZONE, Constants.APADANA_DEFAULT_TIMEZONE)));
        long difference = maintenance_time - now_time;
        difference = difference / 1000;

        return PreferencesManager.getBoolean(mContext, Constants.APADANA_KEY_MAINTENANCE_MODE, false) && difference > 0;
    }

    public boolean hasNewVersion() {
        return PreferencesManager.getInt(mContext, Constants.APADANA_KEY_ANDROID_LAST_VERSION, 0) > mVersionNumber;
    }
}
