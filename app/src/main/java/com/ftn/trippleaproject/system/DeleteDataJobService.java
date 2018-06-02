package com.ftn.trippleaproject.system;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;

import com.ftn.trippleaproject.BuildConfig;
import com.ftn.trippleaproject.TrippleAApplication;
import com.ftn.trippleaproject.domain.Event;
import com.ftn.trippleaproject.domain.NewsArticle;
import com.ftn.trippleaproject.usecase.repository.EventUseCase;
import com.ftn.trippleaproject.usecase.repository.NewsArticleUseCase;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

@EService
public class DeleteDataJobService extends JobService {

    private static final int DELETE_DATA_JOB_ID = 101;

    public static final int NUMBER_OF_NEWS_TO_KEEP = 20;

    public static final int NUMBER_OF_EVENTS_TO_KEEP = 10;

    @App
    static TrippleAApplication trippleAApplication;

    @Inject
    NewsArticleUseCase newsArticleUseCase;

    @Inject
    EventUseCase eventUseCase;

    @Override
    public void onCreate() {
        trippleAApplication.getDiComponent().inject(this);
        super.onCreate();
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        deleteData(params);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }

    public static void scheduleDeleteDataJobService(Context context) {
        final JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        if (jobScheduler == null) {
            return;
        }

        if (isJobServiceOn(jobScheduler)) {
            return;
        }

        jobScheduler.cancel(DELETE_DATA_JOB_ID);

        final long period = !BuildConfig.DEBUG ? TimeUnit.HOURS.toMillis(24): TimeUnit.MINUTES.toMillis(15);

        jobScheduler.schedule(new JobInfo.Builder(DELETE_DATA_JOB_ID,
                new ComponentName(context, DeleteDataJobService_.class))
                // This scheduled job persists even after reboot
                .setPersisted(true)
                .setPeriodic(period)
                .build());
    }

    private static boolean isJobServiceOn(JobScheduler jobScheduler) {
        for (JobInfo jobInfo : jobScheduler.getAllPendingJobs()) {
            if (jobInfo.getId() == DELETE_DATA_JOB_ID) {
                return true;
            }
        }

        return false;
    }

    private void deleteData(JobParameters params) {
        checkDeleteExcessNews();
        checkDeleteExcessEvents();
        jobFinished(params, false);
    }

    private void checkDeleteExcessNews() {
        final List<NewsArticle> newsArticles = newsArticleUseCase.readAllLocal().blockingFirst();

        if (newsArticles.size() > NUMBER_OF_NEWS_TO_KEEP) {
            deleteExcessNews(newsArticles);
        }
    }

    private void deleteExcessNews(List<NewsArticle> newsArticles) {
        Collections.sort(newsArticles, new NewsArticleDateComparator());
        final List<NewsArticle> newsArticlesToBeDeleted = new ArrayList<>();
        for (int i = NUMBER_OF_NEWS_TO_KEEP; i < newsArticles.size(); i++) {
            newsArticlesToBeDeleted.add(newsArticles.get(i));
        }

        newsArticleUseCase.delete(newsArticlesToBeDeleted).blockingSubscribe();
    }

    private void checkDeleteExcessEvents() {
        final List<Event> events = eventUseCase.readAllLocal().blockingFirst();

        if (events.size() > NUMBER_OF_EVENTS_TO_KEEP) {
            deleteExcessEvents(events);
        }
    }

    private void deleteExcessEvents(List<Event> events) {
        Collections.sort(events, new EventEndDateComparator());
        final List<Event> eventsToBeDeleted = new ArrayList<>();
        for (int i = NUMBER_OF_EVENTS_TO_KEEP; i < events.size(); i++) {
            eventsToBeDeleted.add(events.get(i));
        }

        eventUseCase.delete(eventsToBeDeleted).blockingSubscribe();
    }

    private class NewsArticleDateComparator implements Comparator<NewsArticle> {
        @Override
        public int compare(NewsArticle o1, NewsArticle o2) {
            if (o1.getDate().equals(o2.getDate())) {
                return 0;
            } else {
                if (o1.getDate().after(o2.getDate())) {
                    return -1;
                }
            }

            return 1;
        }
    }

    private class EventEndDateComparator implements Comparator<Event> {

        @Override
        public int compare(Event o1, Event o2) {
            if (o1.getEndDate().equals(o2.getEndDate())) {
                return 0;
            } else {
                if (o1.getEndDate().after(o2.getEndDate())) {
                    return -1;
                }
            }

            return 1;
        }
    }
}
