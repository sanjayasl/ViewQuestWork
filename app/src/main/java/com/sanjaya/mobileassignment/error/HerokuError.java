package com.sanjaya.mobileassignment.error;

import java.io.IOException;

public class HerokuError extends IOException {
    private int responseCode;
    private String message;

    public int getResponseCode() {
        return responseCode;
    }

    @Override
    public String getMessage() {
        return message;
    }


    public HerokuError(int responseCode, String message) {
        this.responseCode = responseCode;
        this.message = message;
    }

}
