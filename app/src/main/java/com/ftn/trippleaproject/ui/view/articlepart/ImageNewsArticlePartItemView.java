package com.ftn.trippleaproject.ui.view.articlepart;

import android.content.Context;
import android.net.Uri;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ftn.trippleaproject.R;
import com.ftn.trippleaproject.domain.articlepart.ImageNewsArticlePart;
import com.ftn.trippleaproject.domain.articlepart.NewsArticlePart;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.item_view_image_news_article_part)
public class ImageNewsArticlePartItemView extends NewsArticlePartItemView {

    @ViewById
    SimpleDraweeView image;

    public ImageNewsArticlePartItemView(Context context) {
        super(context);
    }

    @Override
    public void bind(NewsArticlePart newsArticlePart) {
        image.setImageURI(Uri.parse(((ImageNewsArticlePart)newsArticlePart).getUrl()));
    }
}
