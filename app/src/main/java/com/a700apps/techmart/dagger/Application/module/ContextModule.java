package com.a700apps.techmart.dagger.Application.module;

import android.content.Context;

import com.a700apps.techmart.dagger.Application.scope.ApplicationScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mohamed.arafa on 8/27/2017.
 */

@Module
public class ContextModule {

    Context context;

    public ContextModule(Context context) {
        this.context = context;
    }

    @Provides
    @ApplicationScope
    public Context context(){
        return context;
    }

}
