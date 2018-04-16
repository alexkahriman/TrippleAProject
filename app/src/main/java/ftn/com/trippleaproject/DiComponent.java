package ftn.com.trippleaproject;

import javax.inject.Singleton;

import dagger.Component;
import ftn.com.trippleaproject.repository.local.LocalRepositoryModule;
import ftn.com.trippleaproject.repository.remote.RemoteRepositoryModule;
import ftn.com.trippleaproject.ui.fragment.NewsFeedFragment;
import ftn.com.trippleaproject.usecase.UseCaseModule;

@Component(modules = {UseCaseModule.class, RemoteRepositoryModule.class, LocalRepositoryModule.class})
@Singleton
public interface DiComponent {

    void inject(NewsFeedFragment newsFeedFragment);
}
