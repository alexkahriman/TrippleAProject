package com.ftn.trippleaproject.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

import com.ftn.trippleaproject.domain.NewsArticle;
import com.ftn.trippleaproject.ui.adapter.generic.RecyclerViewAdapterBase;
import com.ftn.trippleaproject.ui.adapter.generic.ViewWrapper;
import com.ftn.trippleaproject.ui.view.NewsArticleItemView;
import com.ftn.trippleaproject.ui.view.NewsArticleItemView_;

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
