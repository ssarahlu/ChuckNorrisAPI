package com.example.inspire;

import com.example.inspire.Entities.Joke;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JokeService {

    //GET method to get a random joke in the developer category endpoint
    @GET("/jokes/random?category=dev")
    Call<Joke> getJoke();


}
