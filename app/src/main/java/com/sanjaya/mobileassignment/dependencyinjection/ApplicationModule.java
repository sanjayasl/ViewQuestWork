package com.sanjaya.mobileassignment.dependencyinjection;

import android.content.Context;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sanjaya.mobileassignment.HerokuApplication;
import com.sanjaya.mobileassignment.data.HerokuRestAdapter;
import com.sanjaya.mobileassignment.data.UrlManager;
import com.sanjaya.mobileassignment.data.UserDataSourceImpl;
import com.sanjaya.mobileassignment.data.UserDataSourceInterface;
import com.sanjaya.mobileassignment.error.ErrorInterceptor;
import com.sanjaya.mobileassignment.util.BaseSchedulerProvider;
import com.sanjaya.mobileassignment.util.SchedulerProvider;

import java.lang.reflect.Modifier;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * The ViewQuest created for demonstration purpose
 * don't copy/edit this code without author acknowledgement.
 *
 * @author  Sanjaya Ratnayake
 * @version 1.0
 * @since   2018-09-06
 */
@Module(subcomponents = {UserListSubcomponent.class})
public class ApplicationModule {

    private static final String USERS = "users";


    @Provides
    Context provideContext(HerokuApplication application){
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    UserDataSourceInterface provideDataSource(HerokuRestAdapter adapter){
        return new UserDataSourceImpl(adapter);
    }

    @Provides
    @Singleton
    HerokuRestAdapter provideRestAdapter(Retrofit retrofit){
        return new HerokuRestAdapter(retrofit);
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(){
        OkHttpClient client =
                new OkHttpClient.Builder()
                        .addInterceptor(
                                new ErrorInterceptor())
                        //.addNetworkInterceptor(
                        //        new StethoInterceptor())
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .writeTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(20, TimeUnit.SECONDS)
                        .retryOnConnectionFailure(true)
                        .build();

        Gson gson =
            new GsonBuilder()
//                .excludeFieldsWithoutExposeAnnotation()
                    .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                    .serializeNulls()
                    //.registerTypeAdapter(Long.class, new UtilJsonDateSerializer())
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    .create();

        return new Retrofit.Builder()
                .client(client)
                .baseUrl(UrlManager.API_HOST)
                .addConverterFactory(GsonConverterFactory.create(gson))
                //.addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    BaseSchedulerProvider providerScheduler(){
        return new SchedulerProvider();
    }

}
