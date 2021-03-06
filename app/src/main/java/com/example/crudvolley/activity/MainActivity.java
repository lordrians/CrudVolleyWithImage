package com.example.crudvolley.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.crudvolley.BarangAdapter;
import com.example.crudvolley.R;
import com.example.crudvolley.object.Barang;
import com.example.crudvolley.object.Variable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static RecyclerView rvBarang;
    public static ArrayList<Barang> barangArrayList;
    private BarangAdapter barangAdapter;
    private SharedPreferences UserInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserInfo = getSharedPreferences(Variable.SP_USER_FILE,0);
        handlerAuth();
        init();

    }

    private void handlerAuth() {
        Handler handler = new Handler();
        handler.postAtTime(() -> {


            boolean isLoggedIn = UserInfo.getBoolean(Variable.IS_LOGGED, false);

            if (!isLoggedIn){
                startActivity(new Intent(getApplicationContext(), AuthActivity.class));
                finish();
            }

        },0);
    }

    private void init() {
        String nama_user = UserInfo.getString(Variable.NAMA_USER,"");
        getSupportActionBar().setTitle(nama_user);
        barangArrayList = new ArrayList<>();
        rvBarang = findViewById(R.id.rv_barang);
        rvBarang.setHasFixedSize(true);
        rvBarang.setLayoutManager(new LinearLayoutManager(this));

        loadBarang();

    }

    private void loadBarang() {
        StringRequest request = new StringRequest(StringRequest.Method.POST, Variable.SHOW_ITEM, response -> {
            try {
                JSONArray barangArr = new JSONArray(response);

                for (int i = 0; i < barangArr.length(); i++){
                    JSONObject object = barangArr.getJSONObject(i);

                    Barang barang = new Barang();
                    barang.setId(object.getInt("id"));
                    barang.setNama(object.getString("nama"));
                    barang.setHarga(object.getInt("harga"));
                    barang.setStok(object.getInt("stok"));
                    barang.setPhoto(object.getString("photo"));
                    barangArrayList.add(barang);
                }
                barangAdapter = new BarangAdapter(getApplicationContext(), barangArrayList);
                rvBarang.setAdapter(barangAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(this, "B", Toast.LENGTH_SHORT).show();
            }

        }, error -> {
            error.printStackTrace();
            Toast.makeText(this, "C", Toast.LENGTH_SHORT).show();
        }){
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                if (response.statusCode == 200){
                    JSONArray barangArr = null;
                    try {
                        barangArr = new JSONArray(response);
                        for (int i = 0; i < barangArr.length(); i++){
                            JSONObject object = barangArr.getJSONObject(i);

                            Barang barang = new Barang();
                            barang.setId(object.getInt("id"));
                            barang.setNama(object.getString("nama"));
                            barang.setHarga(object.getInt("harga"));
                            barang.setStok(object.getInt("stok"));
                            barang.setPhoto(object.getString("photo"));
                            barangArrayList.add(barang);
                        }
                        barangAdapter = new BarangAdapter(getApplicationContext(), barangArrayList);
                        rvBarang.setAdapter(barangAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                return super.parseNetworkResponse(response);
            }
        };

        Volley.newRequestQueue(getApplicationContext()).add(request);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.option_menu_tambah:
                startActivity(new Intent(getApplicationContext(), AddActivity.class));
                return true;

            case R.id.option_menu_logout:
                logout();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }

    private void logout() {
        SharedPreferences.Editor editor = UserInfo.edit();
        editor.putBoolean(Variable.IS_LOGGED,false);
        editor.putString(Variable.NAMA_USER, null);
        editor.apply();
        startActivity(new Intent(getApplicationContext(), AuthActivity.class));
        finish();

    }
}