package com.ftn.trippleaproject.ui.fragment;


import android.support.v4.app.Fragment;
import android.view.View;
import android.webkit.WebView;

import com.ftn.trippleaproject.R;
import com.ftn.trippleaproject.TrippleAApplication;
import com.ftn.trippleaproject.domain.NewsArticle;
import com.ftn.trippleaproject.usecase.repository.NewsArticleUseCase;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.IgnoreWhen;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

@EFragment(R.layout.fragment_news_article)
public class NewsArticleFragment extends Fragment implements Consumer<NewsArticle> {

    @App
    TrippleAApplication application;

    @FragmentArg
    NewsArticle newsArticle;

    @ViewById
    WebView webView;

    @Inject
    NewsArticleUseCase newsArticleUseCase;

    @AfterViews
    void init() {

        application.getDiComponent().inject(this);

        newsArticleUseCase.read(newsArticle.getId()).observeOn(AndroidSchedulers.mainThread()).subscribe(this);
    }

    @IgnoreWhen(IgnoreWhen.State.VIEW_DESTROYED)
    @Override
    public void accept(NewsArticle newsArticle) throws Exception {

        if (newsArticle.getContent() == null) {
            return;
        }

        webView.loadData(newsArticle.getContent(), "text/html; charset=utf-8", "UTF-8");
    }
}
