package com.ftn.trippleaproject;

import javax.inject.Singleton;

import dagger.Component;
import com.ftn.trippleaproject.repository.local.LocalRepositoryModule;
import com.ftn.trippleaproject.repository.remote.RemoteRepositoryModule;
import com.ftn.trippleaproject.system.DeleteDataJobService;
import com.ftn.trippleaproject.ui.activity.LoginActivity;
import com.ftn.trippleaproject.ui.fragment.EventFormFragment;
import com.ftn.trippleaproject.ui.fragment.EventFragment;
import com.ftn.trippleaproject.ui.fragment.EventsFragment;
import com.ftn.trippleaproject.ui.fragment.NewsFeedFragment;
import com.ftn.trippleaproject.ui.view.EventItemView;
import com.ftn.trippleaproject.usecase.UseCaseModule;

@Component(modules = {UseCaseModule.class, RemoteRepositoryModule.class, LocalRepositoryModule.class})
@Singleton
public interface DiComponent {

    void inject(LoginActivity loginActivity);

    void inject(NewsFeedFragment newsFeedFragment);

    void inject(EventsFragment eventsFragment);

    void inject(EventFormFragment eventFormFragment);

    void inject(EventFragment eventFragment);

    void inject(EventItemView eventItemView);

    void inject(DeleteDataJobService deleteDataJobService);
}
