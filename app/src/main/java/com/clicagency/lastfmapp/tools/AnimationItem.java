package com.clicagency.lastfmapp.tools;

public class AnimationItem {
    private final String mName;
    private final int mResourceId;

    public AnimationItem(String name, int resourceId) {
        mName = name;
        mResourceId = resourceId;
    }

    public String getName() {
        return mName;
    }

    public int getResourceId() {
        return mResourceId;
    }
}
