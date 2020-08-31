package com.app.tool;

import android.annotation.SuppressLint;
import android.os.Environment;

public enum PublicDirectory {

    Alarms(Environment.DIRECTORY_ALARMS),
    Download(Environment.DIRECTORY_DOWNLOADS),
    Movies(Environment.DIRECTORY_MOVIES),
    Music(Environment.DIRECTORY_MUSIC),
    Notifications(Environment.DIRECTORY_NOTIFICATIONS),
    Pictures(Environment.DIRECTORY_PICTURES),
    Podcasts(Environment.DIRECTORY_PODCASTS),
    Ringtones(Environment.DIRECTORY_RINGTONES),

    @SuppressLint("NewApi") Audiobooks(Environment.DIRECTORY_AUDIOBOOKS),
    @SuppressLint("NewApi") DCIM(Environment.DIRECTORY_DOCUMENTS),
    @SuppressLint("NewApi") Documents(Environment.DIRECTORY_DOCUMENTS),
    @SuppressLint("NewApi") Screenshots(Environment.DIRECTORY_SCREENSHOTS);


    private String type;

    PublicDirectory(java.lang.String type) {
        this.type = type;
    }

    public String getDirectory() {
        return type;
    }
}
