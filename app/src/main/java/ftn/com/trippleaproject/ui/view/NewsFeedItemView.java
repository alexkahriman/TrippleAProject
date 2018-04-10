package ftn.com.trippleaproject.ui.view;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import ftn.com.trippleaproject.R;
import ftn.com.trippleaproject.domain.database.News;

@EViewGroup(R.layout.item_view_news_feed)
public class NewsFeedItemView extends RelativeLayout {

    @ViewById
    TextView title;

    private News news;

    private NewsFeedActionListener newsFeedActionListener;

    public NewsFeedItemView(Context context) {
        super(context);
    }

    public void bind(News news, NewsFeedActionListener newsFeedActionListener) {

        this.news = news;
        this.newsFeedActionListener = newsFeedActionListener;

        title.setText(news.getTitle());
    }

    @Click
    void card() {

        if (newsFeedActionListener != null) {
            newsFeedActionListener.newsFeedSelected(news);
        }
    }

    /**
     * Handles actions in news feed list.
     */
    public interface NewsFeedActionListener {

        /**
         * Handles tap on news feed.
         *
         * @param news Tapped event.
         */
        void newsFeedSelected(News news);
    }
}
