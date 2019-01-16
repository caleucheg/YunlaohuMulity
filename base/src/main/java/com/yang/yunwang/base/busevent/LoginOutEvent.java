package com.yang.yunwang.base.busevent;

public class LoginOutEvent {
    boolean isLogunOut;

    public LoginOutEvent(boolean isLogunOut) {

        this.isLogunOut = isLogunOut;
    }

    public boolean isLogunOut() {
        return isLogunOut;
    }

    public void setLogunOut(boolean logunOut) {
        isLogunOut = logunOut;
    }
}
