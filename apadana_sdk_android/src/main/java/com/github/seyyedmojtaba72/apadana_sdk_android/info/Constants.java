package com.github.seyyedmojtaba72.apadana_sdk_android.info;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.github.seyyedmojtaba72.android_utils.PreferencesManager;
import com.github.seyyedmojtaba72.apadana_sdk_android.utils.AppController;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by SeyyedMojtaba on 6/17/17.
 */

public class Constants {
    private static final String TAG = Constants.class.getSimpleName();

    private final Context mContext;
    private final EndPoints mEndPoints;

    // KEYS:
    public static final String APADANA_KEY_TIMEZONE = "apadana_timezone";
    public static final String APADANA_KEY_PROFILE_IMAGE_MAX_FILE_SIZE = "apadana_profile_image_max_file_size";
    public static final String APADANA_KEY_ANDROID_VERSION_CHECK = "apadana_android_version_check";
    public static final String APADANA_KEY_ANDROID_LAST_VERSION = "apadana_android_last_version";
    public static final String APADANA_KEY_ANDROID_MINIMUM_VERSION = "apadana_android_minimum_version";
    public static final String APADANA_KEY_ANDROID_DOWNLOAD_LINK = "apadana_android_download_link";
    public static final String APADANA_KEY_ANDROID_DETAILS = "apadana_android_details";
    public static final String APADANA_KEY_MAINTENANCE_MODE = "apadana_maintenance_mode";
    public static final String APADANA_KEY_MAINTENANCE_TIME = "apadana_maintenance_time";
    public static final int APADANA_KEY_FLAG_PUSH_NOTIFICATION = 1000;
    public static final int APADANA_KEY_FLAG_PUSH_ORDER = 1001;

    // DEFAULTS:
    public static final String APADANA_DEFAULT_TIMEZONE = "Asia/Tehran";
    public static final int APADANA_DEFAULT_PROFILE_IMAGE_MAX_FILE_SIZE = 200;

    public Constants(Context context){
        this.mContext = context;
        this.mEndPoints = new EndPoints(context);
    }


    // ---------------------------------------------

    public void fetchConstants() {

        StringRequest apadanaRequest = new StringRequest(Request.Method.POST, mEndPoints.getOptions(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject responseObject = new JSONObject(response);
                    if (responseObject.getBoolean("status")) {

                        JSONObject extrasObject = responseObject.getJSONObject("extras");

                        PreferencesManager.putString(mContext, APADANA_KEY_TIMEZONE, extrasObject.getString(APADANA_KEY_TIMEZONE.replace("apadana_", "")));
                        PreferencesManager.putInt(mContext, APADANA_KEY_PROFILE_IMAGE_MAX_FILE_SIZE, Integer.parseInt("0" + extrasObject.getString(APADANA_KEY_PROFILE_IMAGE_MAX_FILE_SIZE.replace("apadana_", ""))));

                        PreferencesManager.putBoolean(mContext, APADANA_KEY_ANDROID_VERSION_CHECK, extrasObject.getBoolean(APADANA_KEY_ANDROID_VERSION_CHECK.replace("apadana_", "")));
                        PreferencesManager.putInt(mContext, APADANA_KEY_ANDROID_LAST_VERSION, Integer.parseInt("0" + extrasObject.getString(APADANA_KEY_ANDROID_LAST_VERSION.replace("apadana_", ""))));
                        PreferencesManager.putInt(mContext, APADANA_KEY_ANDROID_MINIMUM_VERSION, Integer.parseInt("0" + extrasObject.getString(APADANA_KEY_ANDROID_MINIMUM_VERSION.replace("apadana_", ""))));
                        PreferencesManager.putString(mContext, APADANA_KEY_ANDROID_DOWNLOAD_LINK, extrasObject.getString(APADANA_KEY_ANDROID_DOWNLOAD_LINK.replace("apadana_", "")));
                        PreferencesManager.putString(mContext, APADANA_KEY_ANDROID_DETAILS, extrasObject.getString(APADANA_KEY_ANDROID_DETAILS.replace("apadana_", "")));

                        PreferencesManager.putBoolean(mContext, APADANA_KEY_MAINTENANCE_MODE, extrasObject.getBoolean(APADANA_KEY_MAINTENANCE_MODE.replace("apadana_", "")));
                        PreferencesManager.putLong(mContext, APADANA_KEY_MAINTENANCE_TIME, Long.parseLong("0" + extrasObject.getLong(APADANA_KEY_MAINTENANCE_TIME.replace("apadana_", ""))));


                    }
                } catch (JSONException e) {
                    VolleyLog.e(TAG, e.getMessage());
                    Log.e(TAG, response);
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                VolleyLog.e(TAG, e.getMessage());
                e.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap();

                return params;
            }
        };


        AppController.getInstance().addToRequestQueue(apadanaRequest);
    }


}
