package ftn.com.trippleaproject.ui.view;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import ftn.com.trippleaproject.R;
import ftn.com.trippleaproject.domain.database.News;

/**
 * Created by aradosevic on 3/31/18.
 */

@EViewGroup(R.layout.item_view_news)
public class NewsItemView extends RelativeLayout {

    @ViewById
    TextView title;

    private News news;

    public NewsItemView(Context context) {
        super(context);
    }

    public void bind(News news) {
        this.news = news;

        title.setText(news.getTitle());
    }
}
