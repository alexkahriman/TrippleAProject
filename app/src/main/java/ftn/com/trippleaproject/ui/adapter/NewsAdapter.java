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
import ftn.com.trippleaproject.ui.view.NewsFeedItemView;
import ftn.com.trippleaproject.ui.view.NewsFeedItemView_;

@EBean
public class NewsAdapter  extends RecyclerViewAdapterBase<News, NewsFeedItemView> {

    @RootContext
    Context context;

    private NewsFeedItemView.NewsFeedActionListener newsFeedActionListener;

    @Override
    protected NewsFeedItemView onCreateItemView(ViewGroup parent, int viewType) {
        return NewsFeedItemView_.build(context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewWrapper<NewsFeedItemView> holder, int position) {
        holder.getView().bind(items.get(position), newsFeedActionListener);
    }

    public void setNews(List<News> news) {

        items = news;
        notifyDataSetChanged();
    }

    public void setNewsFeedActionListener(NewsFeedItemView.NewsFeedActionListener newsFeedActionListener) {
        this.newsFeedActionListener = newsFeedActionListener;
    }
}
