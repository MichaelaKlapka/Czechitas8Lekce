package cz.czechitas.janhanak.czechitas8lekce;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @GET("/movies")
    Call<ArrayList<Movie>> getAllMovies();

    //Formurlencoded - říká, že to má převést do JSON formátu
    // když mám olabelovené fieldama tak nezáleží na pořadí v jakém to posílám
    @FormUrlEncoded
    @POST("/login")
    Call<LoginAnswer> login(
            @Field("user") String user,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("/loginError")
    Call<LoginAnswer> loginError(
            @Field("user") String user,
            @Field("password") String password
    );



}