package com.max.masterproject;

import android.app.Application;


public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();

   // private RequestQueue mRequestQueue;
    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;


    }
    /*public static synchronized AppController getInstance() {
        return mInstance;
    }
    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAg);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mInstance);
        }

        return mRequestQueue;
    }
*/

}
