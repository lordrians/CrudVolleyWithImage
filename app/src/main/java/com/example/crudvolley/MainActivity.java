package com.example.crudvolley;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static RecyclerView rvBarang;
    public static ArrayList<Barang> barangArrayList;
    private BarangAdapter barangAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }

    private void init() {
        barangArrayList = new ArrayList<>();
        rvBarang = findViewById(R.id.rv_barang);
        rvBarang.setHasFixedSize(true);
        rvBarang.setLayoutManager(new LinearLayoutManager(this));

        loadBarang();

    }

    private void loadBarang() {
        StringRequest request = new StringRequest(StringRequest.Method.GET, Variable.SHOW_ITEM, response -> {
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
            }
        }, Throwable::printStackTrace);
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
                Toast.makeText(getApplicationContext(),"Logout", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }
}