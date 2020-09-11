package com.example.gadsleaderboard.ui.submission;

import android.app.Application;
import android.util.Patterns;
import android.webkit.URLUtil;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.gadsleaderboard.R;
import com.example.gadsleaderboard.models.User;
import com.example.gadsleaderboard.network.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubmitViewModel extends AndroidViewModel {

    public SubmitViewModel(@NonNull Application application) {
        super(application);
    }


    private MutableLiveData<User> _user = new MutableLiveData(new User());
    public LiveData<User> user = _user;

    private MutableLiveData<String> _errorMsg = new MutableLiveData();
    LiveData<String> errorMsg = _errorMsg;

    private MutableLiveData<Boolean> _showConfirmationDialog = new MutableLiveData(false);
    public LiveData<Boolean> showConfirmationDialog = _showConfirmationDialog;

    private MutableLiveData<Boolean> _showSuccessDialog = new MutableLiveData(false);
    public LiveData<Boolean> showSuccessDialog = _showSuccessDialog;

    private MutableLiveData<Boolean> _showFailDialog = new MutableLiveData(false);
    public LiveData<Boolean> showFailDialog = _showFailDialog;

    public MutableLiveData<Boolean> successSent = new MutableLiveData<>();


    public void submit() {
        if (validInput()) {
            if (_showConfirmationDialog.getValue()) {
                sendData();
            } else {
                _showConfirmationDialog.setValue(true);
            }
        }
    }


    private void sendData() {
        Call<Void> call = ApiClient.getInstance().submitToGoogleSheet(user.getValue());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                successSent.setValue(true);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                successSent.setValue(false);
            }
        });
    }


    public void showConfirmationDialog(boolean isShow) {
        _showConfirmationDialog.setValue(isShow);
    }

    public void showFailDialog(boolean isShow) {
        _showFailDialog.setValue(isShow);
    }

    public void showSuccessDialog(boolean isShow) {
        _showSuccessDialog.setValue(isShow);
    }

    private boolean validInput() {
        User currentUser = user.getValue();
        boolean noEmptyField = currentUser != null &&
                currentUser.firstName != null && !currentUser.firstName.isEmpty() &&
                currentUser.lastName != null && !currentUser.lastName.isEmpty() &&
                currentUser.email != null && !currentUser.email.isEmpty() &&
                currentUser.projectLink != null && !currentUser.projectLink.isEmpty();

        if (!noEmptyField) {
            _errorMsg.setValue(getApplication().getResources().getString(R.string.all_fields_required));
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(currentUser.email).matches()) {
            _errorMsg.setValue(getApplication().getResources().getString(R.string.invalid_email));
            return false;
        }

        if (!URLUtil.isValidUrl(currentUser.projectLink)) {
            _errorMsg.setValue(getApplication().getResources().getString(R.string.invalid_link));
            return false;
        }

        return true;
    }

}
