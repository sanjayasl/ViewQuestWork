package com.sanjaya.mobileassignment.dependencyinjection;

import com.sanjaya.mobileassignment.list.ViewInterface;
import com.sanjaya.mobileassignment.list.listActivity;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class UserListModule {
    @Binds
    abstract ViewInterface provideFeatureView(listActivity ListActivity);

}
