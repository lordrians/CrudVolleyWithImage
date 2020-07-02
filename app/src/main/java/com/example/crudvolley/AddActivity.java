package com.example.crudvolley;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {
    private ImageView ivPhoto;
    private EditText etNama, etHarga, etStok;
    private Button btnSimpan;
    private ProgressDialog dialog;
    private Bitmap bitmap = null;
    private SharedPreferences preferences;
    private Uri uriPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        init();
    }

    private void init() {
        preferences = getSharedPreferences(Variable.SP_USER_FILE,0);

        ivPhoto = findViewById(R.id.iv_photo_add);
        etNama = findViewById(R.id.et_nama_add);
        etHarga = findViewById(R.id.et_harga_add);
        etStok = findViewById(R.id.et_stok_add);
        btnSimpan = findViewById(R.id.btn_simpan_add);

        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);

        btnSimpan.setOnClickListener(v -> {
            if (validate()){
                AddBarang();
            }
        });

        ivPhoto.setOnClickListener(v -> {
            pickImage();
        });

    }

    private boolean validate() {
        if (etNama.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Field tidak boleh kosong",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etHarga.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Field tidak boleh kosong",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etStok.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Field tidak boleh kosong",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (uriPhoto == null){
            Toast.makeText(getApplicationContext(),"Gambar tidak boleh kosong",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void AddBarang() {

        dialog.setMessage("Saving...");
        dialog.show();

        StringRequest request = new StringRequest(StringRequest.Method.POST, Variable.INSERT_ITEM, response -> {

            try {
                JSONObject barangArr = new JSONObject(response);

                if (barangArr.getBoolean("success")){
                    JSONObject objBarang = barangArr.getJSONObject("data");
                    Barang barang = new Barang();
                    barang.setId(objBarang.getInt("id"));
                    barang.setNama(objBarang.getString("nama"));
                    barang.setPhoto(objBarang.getString("photo"));
                    barang.setHarga(objBarang.getInt("harga"));
                    barang.setStok(objBarang.getInt("stok"));

                    MainActivity.barangArrayList.add(barang);
                    MainActivity.rvBarang.getAdapter().notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), "Berhasil menambahkan", Toast.LENGTH_SHORT).show();
                    finish();
                }
//                JSONObject object = barangArr.getJSONObject(i);




            } catch (JSONException e) {
                e.printStackTrace();
                dialog.dismiss();
            }
            dialog.dismiss();
        }, error -> {
            error.printStackTrace();
            dialog.dismiss();
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("nama", etNama.getText().toString().trim());
                map.put("harga", etHarga.getText().toString().trim());
                map.put("stok", etStok.getText().toString().trim());
                map.put("photo", bitmapToString(bitmap));
                return map;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(request);

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
}