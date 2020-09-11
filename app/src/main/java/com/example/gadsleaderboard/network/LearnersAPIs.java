package com.example.gadsleaderboard.network;

import com.example.gadsleaderboard.models.Learner;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface LearnersAPIs {

    @GET("/api/hours")
    public Call<List<Learner>> getLearningLeaders();

    @GET("/api/skilliq")
    public Call<List<Learner>> getIQLeaders();
}
