package com.gjson.androidtools.utils;

/**
 * Created by gjson on 2016/11/28.
 * Name AndroidToolJniUtil
 * Version 1.0
 */

public class GjsonJni {

    static {
        System.loadLibrary("app");
    }

    public static native String sayHello();

    public static native String converValue();
}

