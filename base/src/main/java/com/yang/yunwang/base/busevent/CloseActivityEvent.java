package com.yang.yunwang.base.busevent;

public class CloseActivityEvent {
    boolean isCloseActivity;
    String fromTag;

    public CloseActivityEvent(boolean isCloseActivity, String fromTag) {
        this.isCloseActivity = isCloseActivity;
        this.fromTag = fromTag;
    }

    public boolean isCloseActivity() {
        return isCloseActivity;
    }

    public void setCloseActivity(boolean closeActivity) {
        isCloseActivity = closeActivity;
    }

    public String getFromTag() {
        return fromTag;
    }

    public void setFromTag(String fromTag) {
        this.fromTag = fromTag;
    }
}
