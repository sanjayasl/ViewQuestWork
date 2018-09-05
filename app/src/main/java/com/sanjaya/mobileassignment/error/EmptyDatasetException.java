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
public class EmptyDatasetException extends IOException {
    private final String message;

    public EmptyDatasetException(){
        this.message = "No User listed found.";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
