package com.ftn.trippleaproject.usecase.repository.dependency.remote;


import java.util.List;

import com.ftn.trippleaproject.domain.NewsArticle;
import io.reactivex.Single;

public interface NewsArticleRemoteDao {

    Single<List<NewsArticle>> read();
}
