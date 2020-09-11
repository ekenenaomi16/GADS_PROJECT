package com.example.gadsleaderboard.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.gadsleaderboard.models.Learner;
import com.example.gadsleaderboard.network.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeadersViewModel extends ViewModel {

    public MutableLiveData<List<Learner>> successRs = new MutableLiveData<>();
    public MutableLiveData<String> failureMsg = new MutableLiveData<>();
    public MutableLiveData<Boolean> showPD = new MutableLiveData<>();

    public void getLeaders(boolean iqLeaders) {
        showPD.setValue(true);
        Call<List<Learner>> call = ApiClient.getInstance().getLeaders(iqLeaders);
        call.enqueue(new Callback<List<Learner>>() {
            @Override
            public void onResponse(Call<List<Learner>> call, Response<List<Learner>> response) {
                showPD.setValue(false);
                successRs.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Learner>> call, Throwable t) {
                showPD.setValue(false);
                failureMsg.setValue(t.getMessage());
            }
        });
    }
}
