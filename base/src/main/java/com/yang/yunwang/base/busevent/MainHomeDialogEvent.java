package com.yang.yunwang.base.busevent;

public class MainHomeDialogEvent {
    boolean isShowDialpg;

    public MainHomeDialogEvent(boolean isShowDialpg) {

        this.isShowDialpg = isShowDialpg;
    }

    public boolean isShowDialpg() {
        return isShowDialpg;
    }

    public void setShowDialpg(boolean showDialpg) {
        isShowDialpg = showDialpg;
    }
}
