package com.mixno.web_debugger.app;

import android.view.View;
import android.view.animation.AlphaAnimation;

public class DataAnim {

    public static void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(500);
        view.startAnimation(anim);
    }

    public static void setScaleAnimation(View view) {
        view.setScaleY(0.0f);
        view.animate().scaleY(1.0f).setDuration(500).start();
    }
}
