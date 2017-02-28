package xyz.cybersapien.newsdroid.sources;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by ogcybersapien on 2/28/2017.
 * Interface for Retrofit Call for NewsAPI Source List
 */

public interface SourceInterface {

    @GET("/v1/sources")
    Call<SourceAPI> getSources();
}
