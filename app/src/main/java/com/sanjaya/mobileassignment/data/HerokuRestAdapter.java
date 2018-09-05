package com.sanjaya.mobileassignment.data;

import com.sanjaya.mobileassignment.datamodel.UserDataModel;

import javax.inject.Inject;

import io.reactivex.Flowable;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class HerokuRestAdapter {

    private final HerokuService heroku;

    public interface HerokuService {
        @GET(UrlManager.USERS)
        Flowable<UserDataModel> getUserFromHeroku(
                @Query("offset") int offset, @Query("limit") int limit);
    }

    @Inject
    public HerokuRestAdapter(Retrofit retrofit) { heroku = retrofit.create(HerokuService.class);}

    public Flowable<UserDataModel> getUsers(final int offset, final int limit){
        return heroku.getUserFromHeroku(offset, limit);
    }
}
