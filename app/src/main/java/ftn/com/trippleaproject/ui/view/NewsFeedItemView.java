package ftn.com.trippleaproject.ui.view;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import ftn.com.trippleaproject.R;
import ftn.com.trippleaproject.domain.database.NewsArticle;

@EViewGroup(R.layout.item_view_news_article)
public class NewsFeedItemView extends RelativeLayout {

    @ViewById
    TextView title;

    private NewsArticle newsArticle;

    private NewsFeedActionListener newsFeedActionListener;

    public NewsFeedItemView(Context context) {
        super(context);
    }

    public void bind(NewsArticle newsArticle, NewsFeedActionListener newsFeedActionListener) {

        this.newsArticle = newsArticle;
        this.newsFeedActionListener = newsFeedActionListener;

        title.setText(newsArticle.getTitle());
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
