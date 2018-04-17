package com.ftn.trippleaproject.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.ftn.trippleaproject.domain.articlepart.ImageNewsArticlePart;
import com.ftn.trippleaproject.domain.articlepart.NewsArticlePart;
import com.ftn.trippleaproject.domain.articlepart.TextNewsArticlePart;
import com.ftn.trippleaproject.ui.adapter.generic.RecyclerViewAdapterBase;
import com.ftn.trippleaproject.ui.adapter.generic.ViewWrapper;
import com.ftn.trippleaproject.ui.view.articlepart.ImageNewsArticlePartItemView_;
import com.ftn.trippleaproject.ui.view.articlepart.NewsArticlePartItemView;
import com.ftn.trippleaproject.ui.view.articlepart.TextNewsArticlePartItemView_;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

@EBean
public class NewsArticlePartsAdapter extends RecyclerViewAdapterBase<NewsArticlePart, NewsArticlePartItemView> {

    @RootContext
    Context context;

    private enum PartType {
        TEXT,
        IMAGE
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof TextNewsArticlePart) {
            return PartType.TEXT.ordinal();
        } else if (items.get(position) instanceof ImageNewsArticlePart) {
            return PartType.IMAGE.ordinal();
        }
        throw new IllegalArgumentException("Item type not supported.");
    }

    @Override
    protected NewsArticlePartItemView onCreateItemView(ViewGroup parent, int viewType) {
        final PartType type = PartType.values()[viewType];
        switch (type) {
            case TEXT:
                return TextNewsArticlePartItemView_.build(context);
            case IMAGE:
                return ImageNewsArticlePartItemView_.build(context);
        }
        throw new IllegalArgumentException("Item type not supported.");
    }

    @Override
    public void onBindViewHolder(@NonNull ViewWrapper<NewsArticlePartItemView> holder, int position) {
        holder.getView().bind(items.get(position));
    }

    public void setNewsArticleParts(List<NewsArticlePart> newsArticleParts) {
        items = newsArticleParts;
        notifyDataSetChanged();
    }
}
