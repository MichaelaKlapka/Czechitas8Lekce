package cz.czechitas.janhanak.czechitas8lekce;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //inicializace Hawku
        Hawk.init(this).build();
        mEmailView = findViewById(R.id.email);
        mPasswordView = findViewById(R.id.password);
        Button mEmailSignInButton = findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin(mEmailView.getText().toString(), mPasswordView.getText().toString());
                //vytahuju si z edittextu ten text a převádím ho na string
            }
        });

        mProgressView = findViewById(R.id.login_progress);
        //ptam se jestli je nekdo prihlaseny / pokud ano, tak ukonci loginactivity a jdi na dalsi
        //pokud bych dělela logout, tak bych musela vymazat logged a přesměrovala na loginactivity
        if (Hawk.contains("logged")) {
            startMainActivityAndFinishThisOne();
        }
    }

    private void attemptLogin(String login, String password) {
        ApiService apiService = RetrofitInstance.getRetrofitInstance().
                create(ApiService.class);
        Call<LoginAnswer> call;
        if (isPasswordValid(password)) {
            call = apiService.login(login, password);
        } else {
            call = apiService.loginError(login, password);
        }
        //dej příkaz do fronty a zavolej
        //callback červený - alt+enter - implement class - musím doimplementovat
        call.enqueue(new Callback<LoginAnswer>() {
            @Override
            public void onResponse(Call<LoginAnswer> call, Response<LoginAnswer> response) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    Toast.makeText(LoginActivity.this, "Jsem přihlášen", Toast.LENGTH_SHORT).show();
                    //ukládám si login, aby se mi pořád nezobrazovala login aktivity když např. vypadne internet,
                    // ukládám si to pod klíčem logged pod jménem usera - vytáhla jsem si jméno usera z odpovědi
                    Hawk.put("logged", response.body().user);
                    startMainActivityAndFinishThisOne();
                } else {
                    Toast.makeText(LoginActivity.this, "chybova hlaska: " + response.code(),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginAnswer> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startMainActivityAndFinishThisOne() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean isPasswordValid(String password) {
        return password.equals("czechitas");
    }
}

