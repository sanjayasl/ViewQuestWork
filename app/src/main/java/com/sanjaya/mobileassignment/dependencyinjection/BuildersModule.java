package com.sanjaya.mobileassignment.dependencyinjection;

import android.app.Activity;

import com.sanjaya.mobileassignment.list.listActivity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;
/**
 * The ViewQuest created for demonstration purpose
 * don't copy/edit this code without author acknowledgement.
 *
 * @author  Sanjaya Ratnayake
 * @version 1.0
 * @since   2018-09-06
 */
@Module
public abstract class BuildersModule {

    //Add Bindings to Sub-Components here
    @Binds
    @IntoMap
    @ActivityKey(listActivity.class)
    abstract AndroidInjector.Factory<? extends Activity>
    bindListActivityInjectorFactory(UserListSubcomponent.Builder builder);
}
