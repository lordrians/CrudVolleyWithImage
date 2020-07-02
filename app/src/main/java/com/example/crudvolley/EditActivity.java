package com.example.crudvolley;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class EditActivity extends AppCompatActivity {
    private ImageView ivPhoto;
    private EditText etNama, etHarga, etStok;
    private Button btnSimpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        init();

    }

    private void init() {
        ivPhoto = findViewById(R.id.iv_photo_edit);
        etNama = findViewById(R.id.et_nama_edit);
        etHarga = findViewById(R.id.et_harga_edit);
        etStok = findViewById(R.id.et_stok_edit);
        btnSimpan = findViewById(R.id.btn_simpan_edit);

    }
}