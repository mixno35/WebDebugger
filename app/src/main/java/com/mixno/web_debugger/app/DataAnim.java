package com.mixno.web_debugger.app;

import android.content.Context;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class DataAnim {

    public static long DURATION_ANIM = 200;

    public static void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(DURATION_ANIM);
        view.startAnimation(anim);
    }

    public static void setAnimationDelete(Context context, View view) {
        Animation anim = AnimationUtils.loadAnimation(context, android.R.anim.slide_out_right);
        anim.setDuration(DURATION_ANIM);
        view.startAnimation(anim);
    }

    public static void setScaleAnimation(View view) {
        view.setScaleY(0.0f);
        view.animate().scaleY(1.0f).setDuration(DURATION_ANIM).start();
    }
}
