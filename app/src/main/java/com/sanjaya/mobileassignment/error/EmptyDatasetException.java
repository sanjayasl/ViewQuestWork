package com.sanjaya.mobileassignment.error;

import java.io.IOException;

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
