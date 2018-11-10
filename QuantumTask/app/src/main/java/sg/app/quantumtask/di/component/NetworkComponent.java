package sg.app.quantumtask.di.component;

import javax.inject.Singleton;

import dagger.Component;
import sg.app.quantumtask.view.SplashActivity;
import sg.app.quantumtask.di.module.NetworkModule;

@Singleton
@Component(modules = NetworkModule.class)
public interface NetworkComponent {
    void inject(SplashActivity splashActivity);
}
