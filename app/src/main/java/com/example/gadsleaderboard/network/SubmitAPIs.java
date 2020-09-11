package com.example.gadsleaderboard.network;

import com.example.gadsleaderboard.models.Learner;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface SubmitAPIs {

    @POST("forms/d/e/1FAIpQLSf9d1TcNU6zc6KR8bSEM41Z1g1zl35cwZr2xyjIhaMAz8WChQ/formResponse")
    @FormUrlEncoded
    public Call<Void> submit(
            @Field("entry.1824927963")String email,
            @Field("entry.1877115667")String firstName,
            @Field("entry.2006916086")String lastName,
            @Field("entry.284483984")String projectLink
            );



}
