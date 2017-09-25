package com.a700apps.techmart.dagger.Application.component;




import com.a700apps.techmart.dagger.Application.module.ApiServiceModule;
import com.a700apps.techmart.dagger.Application.scope.ApplicationScope;
import com.a700apps.techmart.service.ApiService;

import dagger.Component;

/**
 * Created by mohamed.arafa on 8/27/2017.
 */

@ApplicationScope
@Component(modules = {ApiServiceModule.class})
public interface ApplicationComponent {


    ApiService getService();

}
