package com.ftn.trippleaproject.ui.view;

import android.content.Context;
import android.net.Uri;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ftn.trippleaproject.R;
import com.ftn.trippleaproject.domain.NewsArticle;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.item_view_news_article)
public class NewsArticleItemView extends RelativeLayout {

    @ViewById
    TextView title;

    @ViewById
    SimpleDraweeView image;

    private NewsArticle newsArticle;

    private NewsFeedActionListener newsFeedActionListener;

    public NewsArticleItemView(Context context) {
        super(context);
    }

    public void bind(NewsArticle newsArticle, NewsFeedActionListener newsFeedActionListener) {

        this.newsArticle = newsArticle;
        this.newsFeedActionListener = newsFeedActionListener;

        title.setText(newsArticle.getTitle());
        image.setImageURI(Uri.parse(newsArticle.getImageUrl()));
    }

    @Click
    void card() {
        if (newsFeedActionListener != null) {
            newsFeedActionListener.newsFeedSelected(newsArticle);
        }
    }

    /**
     * Handles actions on news feed list.
     */
    public interface NewsFeedActionListener {

        /**
         * Handles tap on news article.
         *
         * @param newsArticle Tapped event.
         */
        void newsFeedSelected(NewsArticle newsArticle);
    }
}
