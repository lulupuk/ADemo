package com.ym.sdk;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
/**
 * Created by Anony on 2018/1/16.
 */

public class YMPay {
    private static YMPay instance;
    private JSONObject jsStr;
    private String productName, productIndex, cost;

    private YMPay() {
    }

    public static YMPay getInstance() {
        if(instance == null) {
            instance = new YMPay();
        }

        return instance;
    }

    public void pay(String payinfo) {
        Log.i("ymsdk", "pay clicked passing " + payinfo);
        try{
            jsStr = new JSONObject(payinfo);
            productName = jsStr.get("productName").toString();
            productIndex = jsStr.get("productIndex").toString();
            cost = jsStr.get("cost").toString();
        }catch(JSONException e){
            e.printStackTrace();
        }
        YMSDK.getInstance().getMainactref().runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(YMSDK.getInstance().getMainactref());
                builder.setTitle("温馨提示");
                builder.setMessage("确认要购买道具吗？\n"+"【这是支付对话框模板，请检查传递的参数】\n"+"必须传递的支付参数：\n\n"
                        + "productName 是   " + productName + "\n"
                        +"productIndex 是   " + productIndex + "\n"
                        +"cost 是   " + cost + "\n\n"
                        +"【注意: productIndex是从1开始的数字表示】"
                        );
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        YMSDK.getInstance().onResult(0, "获取道具成功");
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        YMSDK.getInstance().onResult(2, "取消获取道具");
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
    }
}