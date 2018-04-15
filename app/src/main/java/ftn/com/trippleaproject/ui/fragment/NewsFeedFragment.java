package ftn.com.trippleaproject.ui.fragment;

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

import ftn.com.trippleaproject.R;
import ftn.com.trippleaproject.TrippleAApplication;
import ftn.com.trippleaproject.domain.database.NewsArticle;
import ftn.com.trippleaproject.ui.activity.NewsArticleActivity_;
import ftn.com.trippleaproject.ui.adapter.NewsAdapter;
import ftn.com.trippleaproject.ui.view.NewsArticleItemView;
import ftn.com.trippleaproject.usecase.crud.NewsArticleCrudUseCase;
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
    NewsArticleCrudUseCase newsArticleCrudUseCase;

    @AfterViews
    void init() {

        application.getDiComponent().inject(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(newsAdapter);

        newsArticleCrudUseCase.read().observeOn(AndroidSchedulers.mainThread()).subscribe(this);

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
