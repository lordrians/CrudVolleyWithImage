package com.example.crudvolley.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.crudvolley.R;
import com.example.crudvolley.object.Barang;
import com.example.crudvolley.object.Variable;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EditActivity extends AppCompatActivity {
    private ImageView ivPhoto;
    private EditText etNama, etHarga, etStok;
    private Button btnSimpan;
    private int idBarang, index;
    private Bitmap bitmap = null;
    private Uri uriPhoto;

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

        Barang paketBarang = getIntent().getParcelableExtra("barang");
        etNama.setText(paketBarang.getNama());
        etHarga.setText(String.valueOf(paketBarang.getHarga()));
        etStok.setText(String.valueOf(paketBarang.getStok()));
        idBarang = paketBarang.getId();
        index = paketBarang.getPosition();
        Toast.makeText(getApplicationContext(),paketBarang.getPhoto(),Toast.LENGTH_SHORT).show();
        Glide.with(getApplicationContext())
                .load(Variable.BASE + "img/" + paketBarang.getPhoto())
                .apply(new RequestOptions().centerCrop())
                .into(ivPhoto);

        btnSimpan.setOnClickListener(v -> {
            simpanData();
        });

        ivPhoto.setOnClickListener(v -> {
            pickImage();
        });

    }

    private void pickImage() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .start(this);
    }
    private String bitmapToString(Bitmap bitPhoto){
        if (bitPhoto != null){
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitPhoto.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] imageByte = byteArrayOutputStream.toByteArray();
            return Base64.encodeToString(imageByte, Base64.DEFAULT);
        } else {
            return "";
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK){
                uriPhoto = result.getUri();
                if (uriPhoto != null){
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uriPhoto);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                ivPhoto.setImageURI(uriPhoto);
            }

        }
    }

    private void simpanData() {
        StringRequest request = new StringRequest(StringRequest.Method.POST, Variable.UPDATE_ITEM, response -> {
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")){
                    JSONObject dtbarang = object.getJSONObject("data");
                    Barang paketBarang = new Barang();
                    paketBarang.setId(dtbarang.getInt("id"));
                    paketBarang.setNama(dtbarang.getString("nama"));
                    paketBarang.setHarga(dtbarang.getInt("harga"));
                    paketBarang.setStok(dtbarang.getInt("stok"));
                    paketBarang.setPhoto(dtbarang.getString("photo"));

                    MainActivity.barangArrayList.set(index,paketBarang);
                    MainActivity.rvBarang.getAdapter().notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(),"Sukses",Toast.LENGTH_SHORT).show();
                    finish();
                }
            } catch ( JSONException e) {
                e.printStackTrace();
            }
        },error -> {

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("id", String.valueOf(idBarang));;
                map.put("nama", etNama.getText().toString());
                map.put("harga", etHarga.getText().toString());
                map.put("stok", etStok.getText().toString());
                map.put("photo", bitmapToString(bitmap));
                return map;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(request);
    }
}