package com.sanjaya.mobileassignment.error;

import java.io.IOException;
/**
 * The ViewQuest created for demonstration purpose
 * don't copy/edit this code without author acknowledgement.
 *
 * @author  Sanjaya Ratnayake
 * @version 1.0
 * @since   2018-09-06
 */
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
