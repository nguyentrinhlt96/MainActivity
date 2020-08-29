package com.v3.cookbook.baseview.utils;

import android.app.Activity;
import android.content.Context;

public class ContextUtils {
    private ContextUtils() {
    }

    public static boolean isValidContext(Context context) {
        boolean z = false;
        if (context == null) {
            return false;
        }
        if (!(context instanceof Activity) || !((Activity) context).isFinishing()) {
            z = true;
        }
        return z;
    }
}
