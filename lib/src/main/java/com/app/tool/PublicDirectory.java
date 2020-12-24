package com.app.tool;

import android.os.Build;
import android.os.Environment;

import androidx.annotation.RequiresApi;


public enum PublicDirectory {

    Alarms(Environment.DIRECTORY_ALARMS),
    Download(Environment.DIRECTORY_DOWNLOADS),
    Movies(Environment.DIRECTORY_MOVIES),
    Music(Environment.DIRECTORY_MUSIC),
    Notifications(Environment.DIRECTORY_NOTIFICATIONS),
    Pictures(Environment.DIRECTORY_PICTURES),
    PodCasts(Environment.DIRECTORY_PODCASTS),
    RingTones(Environment.DIRECTORY_RINGTONES),

    AudioBooks(Environment.DIRECTORY_AUDIOBOOKS),
    DCIM(Environment.DIRECTORY_DCIM),
    Screenshots(Environment.DIRECTORY_SCREENSHOTS),

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    Documents(Environment.DIRECTORY_DOCUMENTS);


    private final String type;

    PublicDirectory(java.lang.String type) {
        this.type = type;
    }

    public String getDirectory() {
        return type;
    }
}
