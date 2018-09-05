package com.sanjaya.mobileassignment.dependencyinjection;

import com.sanjaya.mobileassignment.list.listActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Subcomponent(modules = UserListModule.class)
public interface UserListSubcomponent extends AndroidInjector<listActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<listActivity>{}
}
