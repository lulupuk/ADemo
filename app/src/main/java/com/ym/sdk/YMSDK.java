package com.ym.sdk;

/**
 * Created by Anony on 2018/1/16.
 */

import android.app.Activity;

public class YMSDK {
    private static YMSDK instance;
    private Activity mainactref;
    private IYMSDKListener mlistener = new IYMSDKListener() {
        public void onResult(int code, String msg) {
        }
    };

    private YMSDK() {
    }

    public void init(Activity actcontext) {
        this.mainactref = actcontext;
    }

    public Activity getMainactref() {
        return this.mainactref;
    }

    public static YMSDK getInstance() {
        if(instance == null) {
            instance = new YMSDK();
        }

        return instance;
    }

    public void setListenerCallback(IYMSDKListener listener) {
        this.mlistener = listener;
    }

    public void onResult(int code, String msg) {
        this.mlistener.onResult(code, msg);
    }
}
