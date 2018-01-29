package com.github.seyyedmojtaba72.apadana_sdk_android.info;

import android.content.Context;

import com.github.seyyedmojtaba72.android_utils.Encryption;
import com.github.seyyedmojtaba72.android_utils.Fetcher;
import com.github.seyyedmojtaba72.android_utils.PreferencesManager;


/**
 * Created by SeyyedMojtaba on 6/18/17.
 */

public class EndPoints {
    private Context mContext;

    public String SECRET_KEY = "";
    public String HOME_URL = "", SYSTEM_URL;


    public EndPoints(Context context) {
        this.mContext = context;
        this.SECRET_KEY = PreferencesManager.getString(mContext, "apadana_secret_key", "");
        this.HOME_URL = PreferencesManager.getString(mContext, "apadana_home_url", "");
        this.SYSTEM_URL = PreferencesManager.getString(mContext, "apadana_system_url", "");
    }


    public String getApiUrl() {
        return this.HOME_URL + "/rest?";
    }

    public String getProfileImagesUrl() {
        return this.SYSTEM_URL + "/contents/uploads/profile_images";
    }

    public String generateHash(String url) {
        return url + "&hash=" + Encryption.md5(url + SECRET_KEY);
    }

    public String getProfileImage(String profile_image) {
        return getProfileImage(profile_image, "");
    }

    public String getProfileImage(String profile_image, String modified_at) {
        return getProfileImagesUrl() + "/.thumb/" + profile_image + "?" + modified_at;
    }

    public String getOptions() {
        return getApiUrl() + generateHash("action=get_options");
    }

    public String getBlogInfo(String key) {
        return getApiUrl() + generateHash("action=get_bloginfo&key=" + Fetcher.URLEncode(key));
    }

    public String doRegister() { // post arguments : username, password, password_repeat, email, first_name, last_name, mobile_number
        return getApiUrl() + generateHash("action=register");
    }

    public String doLogin() { // post arguments : username, password
        return getApiUrl() + generateHash("action=login");
    }

    public String sendActivation(String member, String type) {
        return getApiUrl() + generateHash("action=send_activation&member=" + member + "&type=" + type);
    }

    public String doActivate(String member, String code) {
        return getApiUrl() + generateHash("action=activate&member=" + member + "&code=" + code);
    }

    public String doForget() { // post arguments : email
        return getApiUrl() + generateHash("action=forget");
    }

    public String updateProfile() { // post arguments : username, email, no_profile_image, first_name, last_name, mobile_number
        return getApiUrl() + generateHash("action=update_profile");
    }

    public String changePassword() { // post arguments : id, current_password, new_password, new_password_repeat
        return getApiUrl() + generateHash("action=change_password");
    }

    public String doLogout() { // post arguments : id
        return getApiUrl() + generateHash("action=logout");
    }

    public String deleteAccount() { // post arguments : id
        return getApiUrl() + generateHash("action=delete_account");
    }

    public String uploadFile(String directory, boolean replace, String member) {
        return getApiUrl() + generateHash("action=upload_file&directory=" + directory + "&replace=" + replace + "&member=" + member);
    }

    public String addRequest() { // post arguments : type, code, data
        return getApiUrl() + generateHash("action=add_request");
    }

    public String deleteRequest() { // post arguments : type, code
        return getApiUrl() + generateHash("action=delete_request");
    }

}
