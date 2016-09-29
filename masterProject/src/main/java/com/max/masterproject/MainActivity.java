package com.max.masterproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ms.mslibrary.Function;
import com.ms.mslibrary.JSONParser;
import com.ms.mslibrary.MyLog;
import com.ms.mslibrary.ServerErrorCallBack;
import com.ms.mslibrary.VolleyCallBack;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("method","user_login");
            jsonObject.put("textFiled","8109892495");
            jsonObject.put("password","123");
            jsonObject.put("device_id","123456");
            jsonObject.put("mob_type","A");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONParser.getInstance(MainActivity.this).parseVollyJSONObject(
                "http://www.hvantagetechnologies.com/Projects-Work/CoLab/admin_panel/user_app/register_logs.php", 1, jsonObject, "Please wait a few secon...", new VolleyCallBack() {
            @Override
            public void success(String response) {


                if(response != null){
                    try {
                        JSONObject mainObject = new JSONObject(response);
                        if (mainObject.getString("status").equalsIgnoreCase("200")) {
                            MyLog.d("loginTask response", response.toString());

                        }else if(mainObject.getString("status").equalsIgnoreCase("400")){
                            Function.warningDialog(MainActivity.this, "Registered email or mobile number  not found. Please check.");
                        }else if(mainObject.getString("status").equalsIgnoreCase("300")){
                            Function.warningDialog(MainActivity.this, "Password not match. Please use correct password.");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new ServerErrorCallBack() {
            @Override
            public void error(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);

                    String msg = jsonObject.getString(JSONParser.VOLLYMSG);

                    Function.errorDialog(MainActivity.this,msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
