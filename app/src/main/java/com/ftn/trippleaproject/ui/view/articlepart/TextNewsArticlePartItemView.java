package com.ftn.trippleaproject.ui.view.articlepart;


import android.content.Context;
import android.widget.TextView;

import com.ftn.trippleaproject.R;
import com.ftn.trippleaproject.domain.articlepart.NewsArticlePart;
import com.ftn.trippleaproject.domain.articlepart.TextNewsArticlePart;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.item_view_text_news_article_part)
public class TextNewsArticlePartItemView extends NewsArticlePartItemView {

    @ViewById
    TextView text;

    public TextNewsArticlePartItemView(Context context) {
        super(context);
    }

    @Override
    public void bind(NewsArticlePart newsArticlePart) {
        text.setText(((TextNewsArticlePart)newsArticlePart).getText());
    }
}
