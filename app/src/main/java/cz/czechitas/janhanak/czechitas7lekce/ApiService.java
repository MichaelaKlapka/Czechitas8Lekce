package cz.czechitas.janhanak.czechitas7lekce;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @GET("/movies")
    Call<ArrayList<Movie>> getAllMovies();


}