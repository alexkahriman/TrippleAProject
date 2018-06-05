package com.ftn.trippleaproject.usecase.repository;

import android.text.format.DateUtils;

import com.ftn.trippleaproject.domain.Event;
import com.ftn.trippleaproject.domain.Location;
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
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.subscribers.TestSubscriber;

import static com.ftn.trippleaproject.system.DeleteDataJobService.NUMBER_OF_EVENTS_TO_KEEP;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EventUseCaseTest {

    @Mock
    private EventLocalDao eventLocalDao;

    @Mock
    private EventRemoteDao eventRemoteDao;

    private EventUseCase eventUseCase;

    private final Event event = new Event(1, "owner", "title", "desc",
            new Date(), new Date(new Date().getTime() + TimeUnit.DAYS.toMillis(1)), new Location(1, 1));

    private final Event event1 = new Event(2, "owner1", "title1", "desc1",
            new Date(), new Date(), new Location(2, 2));

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

    @Test
    public void testReadByIdLocal() {
        // Arrange
        when(eventLocalDao.read(ArgumentMatchers.any(Long.class))).thenReturn(Flowable.just(event));

        // Act
        final TestSubscriber<Event> eventFlowable = eventUseCase.read(event.getId()).test();
        final Event localEvent = eventUseCase.read(event.getId()).blockingFirst();

        // Assert
        verify(eventLocalDao, times(2)).read(event.getId());
        eventFlowable.assertNoErrors().assertValue(foundTestEvent -> {
            assertThat(foundTestEvent.getId(), equalTo(1L));
            return true;
        });
        assertThat(localEvent.getId(), equalTo(1L));
    }

    @Test
    public void testReadLocalAndUpdate() {
        // Arrange
        List<Event> testEvents = new ArrayList<>();
        testEvents.add(event);
        testEvents.add(event1);

        when(eventRemoteDao.read()).thenReturn(Single.just(testEvents));
        when(eventLocalDao.read()).thenReturn(Flowable.just(testEvents));

        // Act
        final TestSubscriber<List<Event>> eventsFlowable = eventUseCase.readAllLocalAndUpdate().test();
        final List<Event> localEvents = eventUseCase.readAllLocalAndUpdate().blockingFirst();

        // Assert
        verify(eventLocalDao, times(2)).create(testEvents);
        verify(eventRemoteDao, times(2)).read();
        eventsFlowable.assertNoErrors().assertValue(foundTestEvents -> {
            assertThat(foundTestEvents.get(0).getId(), equalTo(1L));
            assertThat(foundTestEvents.get(1).getId(), equalTo(2L));
            return true;
        });
        assertThat(localEvents.size(), equalTo(2));
    }

    @Test
    public void testReadLocalAndUpdateOverLimit() {
        // Arrange
        List<Event> testEvents = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_EVENTS_TO_KEEP / 2 + 1; i++) {
            testEvents.add(event);
            testEvents.add(event1);
        }

        when(eventRemoteDao.read()).thenReturn(Single.just(testEvents));
        when(eventLocalDao.read()).thenReturn(Flowable.just(testEvents));

        // Act
        final TestSubscriber<List<Event>> eventsFlowable = eventUseCase.readAllLocalAndUpdate().test();
        final List<Event> localEvents = eventUseCase.readAllLocalAndUpdate().blockingFirst();

        // Assert
        verify(eventLocalDao, times(42)).create(ArgumentMatchers.any(Event.class));
        verify(eventRemoteDao, times(2)).read();
        eventsFlowable.assertNoErrors().assertValue(foundTestEvents -> {
            assertThat(foundTestEvents.get(0).getId(), equalTo(1L));
            assertThat(foundTestEvents.get(1).getId(), equalTo(2L));
            return true;
        });
        assertThat(localEvents.size(), equalTo(42));
    }

    @Test
    public void testCreate() {
        // Arrange
        when(eventRemoteDao.create(ArgumentMatchers.any(Event.class))).thenReturn(Single.just(event));

        // Act
        eventUseCase.create(event).blockingSubscribe();

        // Assert
        verify(eventRemoteDao, times(1)).create(event);
        verify(eventLocalDao, times(1)).create(event);
    }

    @Test
    public void testCreateFail() {
        // Arrange
        when(eventRemoteDao.create(ArgumentMatchers.any(Event.class))).thenReturn(Single.error(new Throwable()));

        // Act
        try {
            eventUseCase.create(event).blockingSubscribe();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Assert
        verify(eventRemoteDao, times(1)).create(event);
        verify(eventLocalDao, times(0)).create(event);
    }

    @Test
    public void testPatch() {
        // Arrange
        when(eventRemoteDao.patch(ArgumentMatchers.any(Event.class))).thenReturn(Single.just(event));

        // Act
        eventUseCase.patch(event).blockingSubscribe();

        // Assert
        verify(eventRemoteDao, times(1)).patch(event);
        verify(eventLocalDao, times(1)).create(event);
    }

    @Test
    public void testPatchFail() {
        // Arrange
        when(eventRemoteDao.patch(ArgumentMatchers.any(Event.class))).thenReturn(Single.error(new Throwable()));

        // Act
        try {
            eventUseCase.patch(event).blockingSubscribe();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Assert
        verify(eventRemoteDao, times(1)).patch(event);
        verify(eventLocalDao, times(0)).create(event);
    }

    @Test
    public void testDelete() {
        // Act
        eventUseCase.delete(event).blockingSubscribe();

        // Assert
        verify(eventLocalDao, times(1)).delete(event);
    }

    @Test
    public void testDeleteBatch() {
        // Arrange
        List<Event> testEvents = new ArrayList<>();
        testEvents.add(event);
        testEvents.add(event1);

        // Act
        eventUseCase.delete(testEvents).blockingSubscribe();

        // Assert
        verify(eventLocalDao, times(1)).delete(testEvents);
    }
}
