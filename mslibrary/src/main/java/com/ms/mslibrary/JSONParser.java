package com.ms.mslibrary;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class JSONParser {

    static Context cx;
    public static String VOLLYSTATUS = "vollyStatus";
    public static String VOLLYMSG = "vollymsg";
    DialogManager myDialogManager;
    private RequestQueue mRequestQueue;

    private static JSONParser ourInstance = new JSONParser();

    public static JSONParser getInstance(Context cx1) {
        cx = cx1;
        return ourInstance;
    }

    // constructor
    public JSONParser() {

    }


    public void parseVollyJSONObject(String url, int method, final JSONObject jsonObject, String diologTxt, final VolleyCallBack successCallBack, final ServerErrorCallBack errorCallBack) {

        MyLog.d("Request ", url +"\n"+jsonObject.toString());

        myDialogManager = new DialogManager();
        myDialogManager.showProcessDialog(cx,diologTxt);

        if(!Function.isOnline(cx)){
            new SweetAlertDialog(cx, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Oops...")
                    .setContentText(cx.getString(R.string.error_check_internet_connection))
                    .setConfirmText("Close")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                        }
                    })
                    .show();
            myDialogManager.stopProcessDialog();
        }else if (method == 0 || method == 1) {
            final JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (method, url, jsonObject, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            MyLog.d("Response ", response.toString());
                            if (response != null) {
                                try {
                                    response.put(VOLLYSTATUS,"200");
                                    response.put(VOLLYMSG,"Success");
                                    successCallBack.success(response.toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            } else {
                                MyLog.d("", "Something went wrong.!>>");
                                try {
                                    response.put(VOLLYSTATUS,"300");
                                    response.put(VOLLYMSG,"Something went wrong.!");
                                    errorCallBack.error(response.toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            myDialogManager.stopProcessDialog();
                        }

                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            String err = (error.getMessage() == null) ? "Data Send Fail" : error.getMessage();
                            MyLog.d("Response: ", "Something went wrong.!"+err);
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("status","100");
                                jsonObject.put(VOLLYSTATUS,"400");
                                jsonObject.put(VOLLYMSG,err);
                                errorCallBack.error(jsonObject.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            myDialogManager.stopProcessDialog();
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    return headers;
                }
            };

            jsObjRequest.setShouldCache(true);
            // Adding request to request queue
            addToRequestQueue(jsObjRequest);
        } else {
            MyLog.d("", "Invalid Request Method");
        }
    }



    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag("JSONPARSER");
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(cx);
        }

        return mRequestQueue;
    }

}
