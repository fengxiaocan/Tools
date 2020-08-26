package com.app.tool;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.app.tool.Direction.*;


@IntDef({LEFT, RIGHT, TOP, BOTTOM})
@Retention(RetentionPolicy.SOURCE)
public @interface Direction {
    int LEFT = 0;
    int RIGHT = 1;
    int TOP = 3;
    int BOTTOM = 4;
}
