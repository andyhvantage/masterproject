package com.ms.mslibrary;

import android.util.Log;

/**
 * Created by MAX on 24-Sep-16.
 */
public class MyLog {

    public static void d(String myTitle, String msg){
        if(true){
            String title = (myTitle.equalsIgnoreCase("")) ? "MYLOG" : myTitle;
            Log.d(title,msg);
        }
    }
}
