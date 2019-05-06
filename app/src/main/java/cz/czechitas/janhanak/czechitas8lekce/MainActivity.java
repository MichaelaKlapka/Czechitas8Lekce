package cz.czechitas.janhanak.czechitas8lekce;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    CustomListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list);

        ApiService apiService = RetrofitInstance.getRetrofitInstance().
                create(ApiService.class);
        Call<ArrayList<Movie>> call = apiService.getAllMovies();
        call.enqueue(new Callback<ArrayList<Movie>>() {
            @Override
            public void onResponse(Call<ArrayList<Movie>> call, Response<ArrayList<Movie>> response) {
                //odpoved kterou zpracujeme
                adapter = new CustomListAdapter(MainActivity.this, response.body());
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ArrayList<Movie>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Chyba", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
