package com.example.crudvolley.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.crudvolley.R;
import com.example.crudvolley.activity.MainActivity;
import com.example.crudvolley.object.Variable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
            login();
        });

        btnSignUp.setOnClickListener(v -> {
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fl_auth_activity, new SignUpFragment())
                    .commit();
        });

    }

    private void login() {
        Toast.makeText(getContext(), "Test", Toast.LENGTH_LONG).show();
        StringRequest request = new StringRequest(StringRequest.Method.POST, Variable.LOGIN, response -> {
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")){
                    JSONObject user = object.getJSONObject("user");
                    SharedPreferences preferences = getActivity().getSharedPreferences(Variable.SP_USER_FILE,0);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(Variable.NAMA_USER, user.getString("nama"));
                    editor.putBoolean(Variable.IS_LOGGED,true);
                    editor.apply();

                    startActivity(new Intent(getContext(), MainActivity.class));
                    getActivity().finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        },error -> {
            error.printStackTrace();
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("username", etUsername.getText().toString());
                map.put("password", etPassword.getText().toString());
                return map;
            }
        };
        Volley.newRequestQueue(getContext()).add(request);
    }
}