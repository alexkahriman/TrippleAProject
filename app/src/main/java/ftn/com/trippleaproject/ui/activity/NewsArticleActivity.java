package ftn.com.trippleaproject.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import ftn.com.trippleaproject.R;
import ftn.com.trippleaproject.domain.database.NewsArticle;

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

        title.setText(newsArticle.getTitle());
    }
}
