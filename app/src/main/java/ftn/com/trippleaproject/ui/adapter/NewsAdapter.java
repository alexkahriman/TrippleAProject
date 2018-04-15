package ftn.com.trippleaproject.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

import ftn.com.trippleaproject.domain.NewsArticle;
import ftn.com.trippleaproject.ui.adapter.generic.RecyclerViewAdapterBase;
import ftn.com.trippleaproject.ui.adapter.generic.ViewWrapper;
import ftn.com.trippleaproject.ui.view.NewsArticleItemView;
import ftn.com.trippleaproject.ui.view.NewsArticleItemView_;

@EBean
public class NewsAdapter  extends RecyclerViewAdapterBase<NewsArticle, NewsArticleItemView> {

    @RootContext
    Context context;

    private NewsArticleItemView.NewsFeedActionListener newsFeedActionListener;

    @Override
    protected NewsArticleItemView onCreateItemView(ViewGroup parent, int viewType) {
        return NewsArticleItemView_.build(context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewWrapper<NewsArticleItemView> holder, int position) {
        holder.getView().bind(items.get(position), newsFeedActionListener);
    }

    public void setNews(List<NewsArticle> newsArticles) {

        items = newsArticles;
        notifyDataSetChanged();
    }

    public void setNewsFeedActionListener(NewsArticleItemView.NewsFeedActionListener newsFeedActionListener) {
        this.newsFeedActionListener = newsFeedActionListener;
    }
}
