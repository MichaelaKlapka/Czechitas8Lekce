package cz.czechitas.janhanak.czechitas8lekce;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("/movies")
    Call<ArrayList<Movie>> getAllMovies();


}