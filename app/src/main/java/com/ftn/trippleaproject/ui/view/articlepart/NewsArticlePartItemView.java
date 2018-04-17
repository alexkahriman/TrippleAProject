package com.ftn.trippleaproject.ui.view.articlepart;


import android.content.Context;
import android.widget.RelativeLayout;

import com.ftn.trippleaproject.domain.articlepart.NewsArticlePart;

import org.androidannotations.annotations.EViewGroup;

@EViewGroup
public abstract class NewsArticlePartItemView extends RelativeLayout {

    public NewsArticlePartItemView(Context context) {
        super(context);
    }

    public abstract void bind(NewsArticlePart newsArticlePart);
}
