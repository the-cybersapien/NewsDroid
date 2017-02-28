package xyz.cybersapien.newsdroid.article;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by ogcybersapien on 2/28/2017.
 * Interface for calling Retrofit for Stories
 */

public interface ArticleInterface {

    @GET("/v1/articles")
    Call<ArticleAPI> getArticles(@QueryMap Map<String, String> options);
}
