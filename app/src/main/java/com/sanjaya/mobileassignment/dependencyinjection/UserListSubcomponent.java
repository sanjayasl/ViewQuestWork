package com.sanjaya.mobileassignment.dependencyinjection;

import com.sanjaya.mobileassignment.list.listActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
/**
 * The ViewQuest created for demonstration purpose
 * don't copy/edit this code without author acknowledgement.
 *
 * @author  Sanjaya Ratnayake
 * @version 1.0
 * @since   2018-09-06
 */
@Subcomponent(modules = UserListModule.class)
public interface UserListSubcomponent extends AndroidInjector<listActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<listActivity>{}
}
