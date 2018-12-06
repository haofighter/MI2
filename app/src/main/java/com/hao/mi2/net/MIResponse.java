package com.hao.mi2.net;

public class MIResponse<T> {
    T t;
    Exception e;

    public MIResponse(T t, Exception e) {
        this.t = t;
        this.e = e;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public Exception getE() {
        return e;
    }

    public void setE(Exception e) {
        this.e = e;
    }
}
