package ftn.com.trippleaproject.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

import ftn.com.trippleaproject.domain.database.News;
import ftn.com.trippleaproject.ui.adapter.generic.RecyclerViewAdapterBase;
import ftn.com.trippleaproject.ui.adapter.generic.ViewWrapper;
import ftn.com.trippleaproject.ui.view.NewsItemView;
import ftn.com.trippleaproject.ui.view.NewsItemView_;

/**
 * News {@link android.support.v7.widget.RecyclerView RecyclerView} adapter.
 */

@EBean
public class NewsAdapter  extends RecyclerViewAdapterBase<News, NewsItemView> {

    @RootContext
    Context context;

    private NewsItemView.NewsActionListener newsActionListener;

    @Override
    protected NewsItemView onCreateItemView(ViewGroup parent, int viewType) {
        return NewsItemView_.build(context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewWrapper<NewsItemView> holder, int position) {
        holder.getView().bind(items.get(position), newsActionListener);
    }

    public void setNews(List<News> news) {

        items = news;
        notifyDataSetChanged();
    }

    public void setNewsActionListener(NewsItemView.NewsActionListener newsActionListener) {
        this.newsActionListener = newsActionListener;
    }
}
