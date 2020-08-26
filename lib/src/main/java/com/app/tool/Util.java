package com.app.tool;

import android.app.Application;
import android.content.Context;

class Util {
    protected static Context getContext(){
        return Tools.getContext();
    }

    protected static Application getApplication(){
        return Tools.getApplication();
    }
}
