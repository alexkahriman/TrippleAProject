package com.ftn.trippleaproject.usecase.repository;

import com.ftn.trippleaproject.domain.Event;
import com.ftn.trippleaproject.usecase.repository.dependency.local.EventLocalDao;
import com.ftn.trippleaproject.usecase.repository.dependency.remote.EventRemoteDao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.subscribers.TestSubscriber;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EventUseCaseTest {

    @Mock
    EventLocalDao eventLocalDao;

    @Mock
    EventRemoteDao eventRemoteDao;

    private EventUseCase eventUseCase;

    private final Event event = new Event(1, "owner", "title", "desc",
            new Date(), new Date(), new Event.Location(1, 1));

    private final Event event1 = new Event(2, "owner1", "title1", "desc1",
            new Date(), new Date(), new Event.Location(2, 2));

    @Before
    public void setUp() {
        // Arrange
        eventUseCase = new EventUseCase(eventRemoteDao, eventLocalDao);
    }

    @Test
    public void testReadAllLocal() {
        // Arrange
        List<Event> testEvents = new ArrayList<>();
        testEvents.add(event);
        testEvents.add(event1);

        when(eventLocalDao.read()).thenReturn(Flowable.just(testEvents));
        when(eventRemoteDao.read()).thenReturn(Single.just(testEvents));

        // Act
        final TestSubscriber<List<Event>> eventsFlowable = eventUseCase.readAllLocal().test();
        final List<Event> localEvents = eventUseCase.readAllLocal().blockingFirst();

        // Assert
        verify(eventLocalDao, times(2)).read();
        eventsFlowable.assertNoErrors().assertValue(foundTestEvents -> {
            assertThat(foundTestEvents.get(0).getId(), equalTo(1L));
            assertThat(foundTestEvents.get(1).getId(), equalTo(2L));
            return true;
        });
        assertThat(localEvents.size(), equalTo(2));
    }
}
