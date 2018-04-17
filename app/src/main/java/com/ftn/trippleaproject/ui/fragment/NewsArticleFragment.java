package com.ftn.trippleaproject.ui.fragment;


import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ftn.trippleaproject.R;
import com.ftn.trippleaproject.domain.NewsArticle;
import com.ftn.trippleaproject.ui.adapter.NewsArticlePartsAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_news_article)
public class NewsArticleFragment extends Fragment {

    @FragmentArg
    NewsArticle newsArticle;

    @ViewById
    RecyclerView recyclerView;

    @Bean
    NewsArticlePartsAdapter newsArticlePartsAdapter;

    @AfterViews
    void init() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(newsArticlePartsAdapter);
        newsArticlePartsAdapter.setNewsArticleParts(newsArticle.getParts());
    }
}
