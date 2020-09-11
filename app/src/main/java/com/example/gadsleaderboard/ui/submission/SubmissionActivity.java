package com.example.gadsleaderboard.ui.submission;

import android.graphics.Color;
import android.os.Bundle;

import com.example.gadsleaderboard.databinding.ActivitySubmissionBinding;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.gadsleaderboard.R;


public class SubmissionActivity extends AppCompatActivity {

    private ActivitySubmissionBinding binding;
    private SubmitViewModel submitViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_submission);

        setupViewModel();
        binding.setViewModel(submitViewModel);
        binding.setLifecycleOwner(this);

        binding.backImageButton.setOnClickListener(view -> finish());

        binding.button.setOnClickListener(view -> {
//            hideKeyboard(binding.root)
            submitViewModel.submit();
        });
    }

    private void setupViewModel() {
        submitViewModel = new ViewModelProvider(this).get(SubmitViewModel.class);
        submitViewModel.showConfirmationDialog.observe(this, show -> {
            if (show) showConfirmationAlertDialog();
//            else confirmationAlertDialog.dismiss();
        });
        submitViewModel.showSuccessDialog.observe(this, show -> {
            if (show) showSuccessAlertDialog();
        });
        submitViewModel.showFailDialog.observe(this, show -> {
            if (show) showErrorAlertDialog();
        });
        submitViewModel.errorMsg.observe(this, msg -> {
            showErrorSnackBar(binding.getRoot(), msg);
        });
        submitViewModel.successSent.observe(this, successSent -> {
            submitViewModel.showConfirmationDialog(false);

            if (successSent){
                submitViewModel.showSuccessDialog(true);
            } else{
                submitViewModel.showFailDialog(true);
            }
        });
    }


    // region Dialogs

    private AlertDialog confirmationAlertDialog;

    private void showConfirmationAlertDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.confirmation_dialog, null);
        dialogBuilder.setView(dialogView);

        ImageButton closeButton = dialogView.findViewById(R.id.closeImageButton);
        Button yesButton = dialogView.findViewById(R.id.yesButton);

        confirmationAlertDialog = dialogBuilder.create();
        confirmationAlertDialog.setCanceledOnTouchOutside(false);
        confirmationAlertDialog.show();

        closeButton.setOnClickListener(view -> {
            confirmationAlertDialog.dismiss();
            submitViewModel.showConfirmationDialog(false);
        });
        yesButton.setOnClickListener(view -> {
            submitViewModel.submit();
        });
    }

    private void showErrorAlertDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.error_dialog, null);
        dialogBuilder.setView(dialogView);

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();

        new Handler().postDelayed(() -> {
            submitViewModel.showFailDialog(false);
            alertDialog.dismiss();
        }, 2000);
    }

    private void showSuccessAlertDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.success_dialog, null);
        dialogBuilder.setView(dialogView);

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();

        new Handler().postDelayed(() -> {
            alertDialog.dismiss();
            submitViewModel.showSuccessDialog(false);
            finish();
        }, 2000);
    }

    private void showErrorSnackBar(View view, String msg) {
        Snackbar.make(
                view,
                msg,
                Snackbar.LENGTH_LONG
        ).setBackgroundTint(Color.RED).show();
    }

    // endregion
}

