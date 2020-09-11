package com.example.gadsleaderboard.network;

import com.example.gadsleaderboard.models.Learner;
import com.example.gadsleaderboard.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String BASE_URL = "https://gadsapi.herokuapp.com";
    private static final String GOOGLE_SHEET_URL = "https://docs.google.com";
    private LearnersAPIs learnersAPIs;
    private SubmitAPIs submitAPIs;
    private static ApiClient instance;

    private ApiClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        learnersAPIs = retrofit.create(LearnersAPIs.class);

        //--
        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl(GOOGLE_SHEET_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        submitAPIs = retrofit2.create(SubmitAPIs.class);
    }

    public static ApiClient getInstance() {
        if (instance == null) {
            instance = new ApiClient();
        }
        return instance;
    }

    //===========================================================================
    public Call<List<Learner>> getLeaders(boolean iqLearners) {
        if (iqLearners) {
            return learnersAPIs.getIQLeaders();
        } else {
            return learnersAPIs.getLearningLeaders();
        }
    }

    public Call<Void> submitToGoogleSheet(User user) {
        return submitAPIs.submit(user.email, user.firstName, user.lastName, user.projectLink);
    }

}
