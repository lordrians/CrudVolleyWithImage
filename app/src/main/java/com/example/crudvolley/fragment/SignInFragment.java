package com.example.crudvolley.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.crudvolley.R;

public class SignInFragment extends Fragment {
    private View view;
    private EditText etUsername, etPassword;
    private Button btnLogin;
    private TextView btnSignUp;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        init();
        return view;
    }

    private void init() {
        etUsername = view.findViewById(R.id.et_username_signin);
        etPassword = view.findViewById(R.id.et_password_signin);
        btnLogin = view.findViewById(R.id.btn_login_signin);
        btnSignUp = view.findViewById(R.id.tv_btn_signup_signin);

        btnLogin.setOnClickListener(v -> {

        });

        btnSignUp.setOnClickListener(v -> {
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fl_auth_activity, new SignUpFragment())
                    .commit();
        });

    }
}