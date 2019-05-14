package cz.czechitas.janhanak.czechitas8lekce;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;

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

    //vytvarim menu z xml main_menu, kde jsou jednotlivé položky menu a jak se mají zobrazovat
    // layout je ta bílá plocha - pokud chci menu do toho top baru, tak musím vytvořit samostatné xml
    // pokud dám do jiných aktivit, tak budu mít všude stejné menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection, v případě, že je to id logoutu,
        // tak vymažu logged a zapni aktivitu login aktivity a ukonči aktivitu, kde právě jsi
        switch (item.getItemId()) {
            case R.id.logout:
                Hawk.delete("logged");
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                Toast.makeText(this, "Uživatel byl odhlášen", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
