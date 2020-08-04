package com.example.crudvolley.fragment;

import android.app.ProgressDialog;
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

public class SignUpFragment extends Fragment {
    private EditText etName, etUsername, etPassword;
    private Button btnRegister;
    private TextView btnSignIn;
    private View view;
    private ProgressDialog dialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        init();
        return view;
    }

    private void init() {

        etName = view.findViewById(R.id.et_name_signup);
        etPassword = view.findViewById(R.id.et_password_signup);
        etUsername = view.findViewById(R.id.et_username_signup);
        btnRegister = view.findViewById(R.id.btn_register_signup);
        btnSignIn = view.findViewById(R.id.tv_btn_signin_signup);

        dialog = new ProgressDialog(getContext());
        dialog.setCancelable(false);

        btnSignIn.setOnClickListener(v -> {
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fl_auth_activity, new SignInFragment())
                    .commit();
        });

        btnRegister.setOnClickListener(v -> {
            register();
        });

    }

    private void register() {
        dialog.setMessage("Sign Up...");
        dialog.show();
        StringRequest request = new StringRequest(StringRequest.Method.POST, Variable.REGISTER, response -> {
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")){
                    SharedPreferences preferences = getActivity().getSharedPreferences(Variable.SP_USER_FILE,0);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean(Variable.IS_LOGGED, true);
                    editor.putString(Variable.NAMA_USER,etName.getText().toString().trim());
                    editor.apply();

                    startActivity(new Intent(getActivity(), MainActivity.class));
                    getActivity().finish();
                }
                dialog.dismiss();
            } catch (JSONException e) {
                e.printStackTrace();
                dialog.dismiss();
            }
        },error -> {
            error.printStackTrace();
            dialog.dismiss();
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("nama", etName.getText().toString().trim());
                map.put("username", etUsername.getText().toString().trim());
                map.put("password", etPassword.getText().toString().trim());
                return map;
            }
        };
        Volley.newRequestQueue(getContext()).add(request);
    }
}