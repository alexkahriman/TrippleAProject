package ftn.com.trippleaproject.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import ftn.com.trippleaproject.R;
import ftn.com.trippleaproject.domain.database.News;

@EActivity(R.layout.activity_news_article)
public class NewsArticleActivity extends AppCompatActivity {

    @Extra
    News news;

    @ViewById
    TextView title;

    @AfterViews
    void init() {

        if (news == null) {
            return;
        }

        title.setText(news.getTitle());
    }
}
