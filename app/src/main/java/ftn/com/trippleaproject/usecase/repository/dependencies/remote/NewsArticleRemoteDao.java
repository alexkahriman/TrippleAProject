package ftn.com.trippleaproject.usecase.repository.dependencies.remote;


import java.util.List;

import ftn.com.trippleaproject.domain.NewsArticle;
import io.reactivex.Single;

public interface NewsArticleRemoteDao {

    Single<List<NewsArticle>> read();
}
