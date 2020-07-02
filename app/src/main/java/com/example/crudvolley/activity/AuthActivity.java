package com.example.crudvolley.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;

import com.example.crudvolley.R;
import com.example.crudvolley.fragment.SignInFragment;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        loadFragment(new SignInFragment());

    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_auth_activity, fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really wan't to exit?")
                .setMessage("Are you sure want to exit?")
                .setNegativeButton(R.string.no, null)
                .setPositiveButton(R.string.yes, (dialog, which) -> finish()).create().show();
    }
}