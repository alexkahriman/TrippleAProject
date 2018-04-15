package ftn.com.trippleaproject;

import javax.inject.Singleton;

import dagger.Component;
import ftn.com.trippleaproject.ui.fragment.NewsFeedFragment;
import ftn.com.trippleaproject.usecase.UseCaseModule;

@Component(modules = UseCaseModule.class)
@Singleton
public interface DiComponent {

    void inject(NewsFeedFragment newsFeedFragment);
}
