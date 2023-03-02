package com.sum.stater.utils;

import android.util.Log;

public class LaunchTimer {

    private static long sTime;

    public static void startRecord() {
        sTime = System.currentTimeMillis();
    }

    public static void endRecord() {
        endRecord("");
    }

    public static void endRecord(String msg) {
        long cost = System.currentTimeMillis() - sTime;
        Log.i("LaunchTimer", msg + "cost " + cost);
    }

}
