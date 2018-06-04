package com.ftn.trippleaproject.system.comparator;

import com.ftn.trippleaproject.domain.NewsArticle;

import java.util.Comparator;

public class NewsArticleDateComparator implements Comparator<NewsArticle> {
    @Override
    public int compare(NewsArticle o1, NewsArticle o2) {
        if (o1.getDate().equals(o2.getDate())) {
            return 0;
        } else {
            if (o1.getDate().after(o2.getDate())) {
                return -1;
            }
        }

        return 1;
    }
}
