package com.ftn.trippleaproject.ui.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.ftn.trippleaproject.R;
import com.ftn.trippleaproject.domain.NewsArticle;
import com.ftn.trippleaproject.ui.fragment.NewsArticleFragment;
import com.ftn.trippleaproject.ui.fragment.NewsArticleFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_news_article)
public class NewsArticleActivity extends AppCompatActivity {

    @Extra
    NewsArticle newsArticle;

    @ViewById
    TextView title;

    @AfterViews
    void init() {

        if (newsArticle == null) {
            return;
        }

        final FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        NewsArticleFragment newsArticleFragment = NewsArticleFragment_.builder().newsArticle(newsArticle).build();
        fragmentTransaction.replace(R.id.newsArticleFragmentContainer, newsArticleFragment);
        fragmentTransaction.commit();
    }
}
