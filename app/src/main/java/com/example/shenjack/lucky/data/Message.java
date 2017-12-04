package com.example.shenjack.lucky.data;

/**
 * sjjkk on 2017/12/3 in Beijing.
 */

public class Message {
    public Message(String message, String handle) {
        this.message = message;
        this.handle = handle;
    }

    public String message = null;
    public String handle = null;
    public Double timeStamp = null;
    public String  timeString = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public double getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(double timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getTimeString() {
        return timeString;
    }

    public void setTimeString(String timeString) {
        this.timeString = timeString;
    }
}
