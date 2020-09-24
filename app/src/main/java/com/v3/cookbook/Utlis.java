package com.v3.cookbook;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import java.util.Hashtable;

public class Utlis {
    public static final boolean isEnable = true;
    public static final String AD_UNIT_BANNER = "ca-app-pub-1945007005893167/6201035287";
    public static final int HOME = 1;
    public static final int FAVAURITE = 2;
    public static  int currentState = 0 ;
    public static final String TEST_DEVICE ="56E989955BCD54FF1A315C840C9DB87";
   // public static final String APP_ID = "202366405";
   public static final String APP_ID = "demo";
    private static final Hashtable<String, Typeface> CACHE = new Hashtable<>();

    public static void hideKeyboard(View focusingView, Activity context) {
        try {
            InputMethodManager imm = (InputMethodManager) context.getSystemService("input_method");
            if (focusingView != null) {
                imm.hideSoftInputFromWindow(focusingView.getWindowToken(), 2);
            } else {
                imm.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), 2);
            }
        } catch (Exception e) {
        }
    }

    public static String modifyDropboxUrl(String originalUrl) {
        String str = "dl.dropboxusercontent.";
        return originalUrl.replace("www.dropbox.", str).replace("dropbox.", str);
    }

    public static Typeface getTypefaceRobotoNormal(Context context) {
        return get(context, "fonts/roboto_regular.ttf");
    }

    public static Typeface get(Context context, String assetPath) {
        return cachedTypeface(assetPath, Typeface.createFromAsset(context.getAssets(), assetPath));
    }

    private static Typeface cachedTypeface(String assetPath, Typeface t) {
        Typeface typeface;
        synchronized (CACHE) {
            if (!CACHE.containsKey(assetPath)) {
                try {
                    CACHE.put(assetPath, t);
                } catch (Exception e) {
                    return null;
                }
            }
            typeface = (Typeface) CACHE.get(assetPath);
        }
        return typeface;
    }
}
