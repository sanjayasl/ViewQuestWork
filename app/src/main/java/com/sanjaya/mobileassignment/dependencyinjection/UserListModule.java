package com.sanjaya.mobileassignment.dependencyinjection;

import com.sanjaya.mobileassignment.list.ViewInterface;
import com.sanjaya.mobileassignment.list.listActivity;

import dagger.Binds;
import dagger.Module;
/**
 * The ViewQuest created for demonstration purpose
 * don't copy/edit this code without author acknowledgement.
 *
 * @author  Sanjaya Ratnayake
 * @version 1.0
 * @since   2018-09-06
 */
@Module
public abstract class UserListModule {
    @Binds
    abstract ViewInterface provideFeatureView(listActivity ListActivity);

}
