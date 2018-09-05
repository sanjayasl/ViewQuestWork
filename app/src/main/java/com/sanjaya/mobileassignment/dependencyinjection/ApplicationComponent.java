package com.sanjaya.mobileassignment.dependencyinjection;

import com.sanjaya.mobileassignment.HerokuApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        ApplicationModule.class,
        BuildersModule.class
})
public interface ApplicationComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(HerokuApplication application);
        ApplicationComponent build();
    }

    void inject (HerokuApplication application);
}
