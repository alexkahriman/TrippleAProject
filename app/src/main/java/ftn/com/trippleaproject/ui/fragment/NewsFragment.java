package ftn.com.trippleaproject.ui.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import ftn.com.trippleaproject.R;
import ftn.com.trippleaproject.domain.database.News;
import ftn.com.trippleaproject.ui.activity.NewsActivity_;
import ftn.com.trippleaproject.ui.adapter.NewsAdapter;
import ftn.com.trippleaproject.ui.view.NewsItemView;

/**
 * Presents a list of news.
 */

@EFragment(R.layout.fragment_news)
public class NewsFragment extends Fragment implements NewsItemView.NewsActionListener {

    @ViewById
    RecyclerView recyclerView;

    @Bean
    NewsAdapter  newsAdapter;

    @AfterViews
    void init() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(newsAdapter);

        List<News> news = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            news.add(new News("title" + i));
        }

        newsAdapter.setNewsActionListener(this);
        newsAdapter.setNews(news);
    }

    @Override
    public void newsSelected(News news) {
        NewsActivity_.intent(getContext()).news(news).start();
    }
}
