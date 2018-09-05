package com.sanjaya.mobileassignment.error;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;
/**
 * The ViewQuest created for demonstration purpose
 * don't copy/edit this code without author acknowledgement.
 *
 * @author  Sanjaya Ratnayake
 * @version 1.0
 * @since   2018-09-06
 */
public class ErrorInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());

        if (!response.isSuccessful()) {
            throw new HerokuError(
                    response.code(),
                    response.message()
            );
        }
        return response;
    }
}
