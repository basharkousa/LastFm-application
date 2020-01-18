package com.clicagency.lastfmapp.tools;

import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import androidx.annotation.IntDef;

public class AnimationUtils {

    public static final int TRANSITION_POP = 0;
    public static final int TRANSITION_FADE_IN_OUT = 1;
    public static final int TRANSITION_SLIDE_LEFT_RIGHT = 2;
    public static final int TRANSITION_SLIDE_LEFT_RIGHT_WITHOUT_EXIT = 3;
    public static final int TRANSITION_NONE = 4;


    @IntDef({TRANSITION_POP, TRANSITION_FADE_IN_OUT, TRANSITION_SLIDE_LEFT_RIGHT, TRANSITION_SLIDE_LEFT_RIGHT_WITHOUT_EXIT, TRANSITION_NONE})
    @interface FragmentAnimation {}


    public static ScaleAnimation getScaleAnimation(){
        final ScaleAnimation animation = new ScaleAnimation(0f, 1f, 0f, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(165);
        return animation;
    }
}
