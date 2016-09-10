package com.example.hwj.mydemo.NetWork.http.Bean;

/**
 * Created by hwj on 16-9-10.
 */

public class MyResult<T> {
    private int errNum;
    private String retMsg;
    private T retData;

    public int getErrNum() {
        return errNum;
    }

    public void setErrNum(int errNum) {
        this.errNum = errNum;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public T getRetData() {
        return retData;
    }

    public void setRetData(T retData) {
        this.retData = retData;
    }
}
