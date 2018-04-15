package ftn.com.trippleaproject.usecase.crud;

import java.util.List;

import ftn.com.trippleaproject.domain.database.NewsArticle;
import io.reactivex.Flowable;

public interface NewsArticleCrudUseCase {

    Flowable<List<NewsArticle>> read();
}
