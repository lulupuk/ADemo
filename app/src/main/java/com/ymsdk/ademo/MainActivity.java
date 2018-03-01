package com.ymsdk.ademo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.ym.sdk.IYMSDKListener;
import com.ym.sdk.YMPay;
import com.ym.sdk.YMSDK;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "SDKDemo";
    /**
     * 支付状态码，cp自行修改
     */
    public static final int PAY_SUCCESS = 0;//支付成功
    public static final int PAY_FAIL = 1;//支付失败
    public static final int PAY_CANCEL = 2;//支付取消

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //支付回调设置
        YMSDK.getInstance().setListenerCallback(new IYMSDKListener() {

            @Override
            public void onResult(final int code, final String msg) {
                switch(code){
                    case PAY_FAIL:
                        Log.d(TAG, "支付失败");
                        buyFail(msg);
                        break;
                    case PAY_SUCCESS:
                        Log.d(TAG, "支付成功");
                        buySuccess(msg);
                        break;
                    case PAY_CANCEL:
                        Log.d(TAG, "支付取消");
                        buyFail(msg);
                        break;
                    default:
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
                }
            }
        });

        YMSDK.getInstance().init(this);

    }

    //支付接口 必接
    public void pay(View v)
    {
        JSONObject json = new JSONObject();
        try {
            json.put("productName", "test_product1");//道具名称，必须
            json.put("productIndex", "1");//道具编号，必须
            json.put("cost", "2000");// 支付金额(单位：分)，必须
            json.put("cpCustomInfo", "游戏方自定义json格式数据");// 游戏方自定义json格式数据，可选
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String payinfo = json.toString();
        YMPay.getInstance().pay(payinfo);
    }

    private void buySuccess(String msg)
    {
        Toast.makeText(this,"支付成功回调，勿删此调用" ,Toast.LENGTH_SHORT).show();
        /**
         *   此处填写支付成功回调游戏逻辑
        **/
        Log.i(TAG,"支付回调信息：" + msg);
    }

    private void buyFail(String msg)
    {
        Toast.makeText(this,"支付失败回调，勿删此调用" ,Toast.LENGTH_SHORT).show();
        /**
         *   此处填写支付失败回调游戏逻辑
         **/
        Log.i(TAG,"支付回调信息：" + msg);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitGame();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exitGame() {
        //退出接口 必接
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("温馨提示");
        builder.setMessage("确认要退出游戏吗？【这是模板对话框】");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.this.finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    //游戏事件通知接口
    public void SendEvent(String event){
        Log.i(TAG,"游戏事件通知：" + event);
    }

}
