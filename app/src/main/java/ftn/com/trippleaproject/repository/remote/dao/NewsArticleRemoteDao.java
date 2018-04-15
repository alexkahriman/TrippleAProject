package ftn.com.trippleaproject.repository.remote.dao;


import java.util.List;

import ftn.com.trippleaproject.domain.database.NewsArticle;
import io.reactivex.Single;

public interface NewsArticleRemoteDao {

    Single<List<NewsArticle>> read();
}
