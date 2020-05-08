package com.example.inspire;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inspire.Entities.Joke;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private Button button, copyButton;
    private ImageButton copyIcon;
    private TextView joke;
    private static final String TAG = "MainActivity";
    private String mValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        copyButton = findViewById(R.id.copyButton);
        copyIcon = findViewById(R.id.copyIcon);
        joke = findViewById(R.id.joke);

        //sets the copy buttons invisible before the joke is loaded
        copyButton.setVisibility(View.GONE);
        copyIcon.setVisibility(View.GONE);

        //sets the title
        MainActivity.this.setTitle("Inspired by Chuck Norris");

        //sets the TextView for when a user first loads the app that shows instructions
        joke.setText("Feeling uninspired? \n Click the button below to get your daily dose of programming inspiration by Chuck Norris!");

        //sets an onClickListener for the button to call the loadJoke method when clicked
        button.setOnClickListener(v -> loadJoke());

        //sets an onClickListener for the button to copy the text to clipboard
        copyButton.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            if (mValue != null) {
                //copies the quote to clipboard
                ClipData clip = ClipData.newPlainText("Copied Text", mValue);
                clipboard.setPrimaryClip(clip);
                //shows small popup saying that the quote has been copied
                Toast.makeText(getApplicationContext(), "Quote copied to clipboard!", Toast.LENGTH_SHORT).show();
            }
        });

        //sets an onClickListener for the icon to copy the text to clipboard
        copyIcon.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            if (mValue != null) {
                //copies the quote to clipboard
                ClipData clip = ClipData.newPlainText("Copied Text", mValue);
                clipboard.setPrimaryClip(clip);
                //shows small popup saying that the quote has been copied
                Toast.makeText(getApplicationContext(), "Quote copied to clipboard!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //method that loads the joke from the chucknorris api
    public void loadJoke() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.chucknorris.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JokeService service = retrofit.create(JokeService.class);
        Call<Joke> jokeCall = service.getJoke();
        jokeCall.enqueue(new Callback<Joke>() {
            @Override
            public void onResponse(Call<Joke> call, Response<Joke> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse");
                    //gets the value of the Joke object and stores it in a String
                    mValue = response.body().getValue();
                    //sets the joke TextView as the value
                    joke.setText(mValue);
                    //sets the copy buttons visible when the joke is loaded
                    copyButton.setVisibility(View.VISIBLE);
                    copyIcon.setVisibility(View.VISIBLE);
                } else {
                    Log.d(TAG, "onResponse: ERROR IS " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Joke> call, Throwable t) {
                Log.d("Error", t.getLocalizedMessage());
            }

        });
    }

}
