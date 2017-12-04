package com.example.shenjack.lucky.data;

/**
 * sjjkk on 2017/11/27 in Beijing.
 */

public class Response<T> {

    /**
     * status : success
     */
    public T data;

    public String status;
    /**
     * error : 名字或者密码错误
     */

    public String error;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }


}
