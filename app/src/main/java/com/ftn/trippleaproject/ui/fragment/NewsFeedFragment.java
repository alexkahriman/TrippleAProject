package com.ftn.trippleaproject.ui.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.IgnoreWhen;
import org.androidannotations.annotations.ViewById;
import java.util.List;

import javax.inject.Inject;

import com.ftn.trippleaproject.R;
import com.ftn.trippleaproject.TrippleAApplication;
import com.ftn.trippleaproject.domain.NewsArticle;
import com.ftn.trippleaproject.ui.activity.NewsArticleActivity_;
import com.ftn.trippleaproject.ui.adapter.NewsAdapter;
import com.ftn.trippleaproject.ui.view.NewsArticleItemView;
import com.ftn.trippleaproject.usecase.repository.NewsArticleUseCase;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

@EFragment(R.layout.fragment_news_feed)
public class NewsFeedFragment extends Fragment implements Consumer<List<NewsArticle>>, NewsArticleItemView.NewsFeedActionListener {

    @App
    TrippleAApplication application;

    @ViewById
    RecyclerView recyclerView;

    @Bean
    NewsAdapter  newsAdapter;

    @Inject
    NewsArticleUseCase newsArticleUseCase;

    @AfterViews
    void init() {

        application.getDiComponent().inject(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(newsAdapter);

        newsArticleUseCase.read().observeOn(AndroidSchedulers.mainThread()).subscribe(this);

        newsAdapter.setNewsFeedActionListener(this);
    }

    @IgnoreWhen(IgnoreWhen.State.VIEW_DESTROYED)
    @Override
    public void accept(List<NewsArticle> newsArticles) {
        newsAdapter.setNews(newsArticles);
    }

    @Override
    public void newsFeedSelected(NewsArticle newsArticle) {
        NewsArticleActivity_.intent(getContext()).newsArticle(newsArticle).start();
    }
}
