package ftn.com.trippleaproject.ui.view;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import ftn.com.trippleaproject.R;
import ftn.com.trippleaproject.domain.database.News;

/**
 * Presents one news in the list.
 */

@EViewGroup(R.layout.item_view_news)
public class NewsItemView extends RelativeLayout {

    @ViewById
    TextView title;

    private News news;

    private NewsActionListener newsActionListener;

    public NewsItemView(Context context) {
        super(context);
    }

    public void bind(News news, NewsActionListener newsActionListener) {

        this.news = news;
        this.newsActionListener = newsActionListener;

        title.setText(news.getTitle());
    }

    @Click
    void card() {

        if (newsActionListener != null) {
            newsActionListener.newsSelected(news);
        }
    }

    public interface NewsActionListener {

        void newsSelected(News news);
    }
}
