package com.example.crudvolley;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.crudvolley.activity.EditActivity;
import com.example.crudvolley.activity.MainActivity;
import com.example.crudvolley.object.Barang;
import com.example.crudvolley.object.Variable;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BarangAdapter extends RecyclerView.Adapter<BarangAdapter.ViewHolder> {

    private Context mContext;
    private View view;
    private ArrayList<Barang> barangArrayList;

    public BarangAdapter(Context mContext, ArrayList<Barang> barangArrayList){
        this.mContext = mContext;
        this.barangArrayList = barangArrayList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(mContext).inflate(R.layout.item_barang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Barang barang = barangArrayList.get(position);

        holder.tvNama.setText(barang.getNama());
        holder.tvStok.setText(String.valueOf(barang.getStok()));
        holder.tvHarga.setText(String.valueOf(barang.getHarga()));

        Glide.with(mContext)
                .load(Variable.BASE + "img/" + barang.getPhoto())
                .apply(new RequestOptions().centerCrop())
                .into(holder.ivPhoto);

        holder.itemLayout.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(mContext, holder.tvStok);
            popupMenu.inflate(R.menu.item_menu);
            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()){
                    case R.id.menu_hapus:
                        deleteItem(barang.getId(),position, barang.getPhoto() );
                        break;
                    case R.id.menu_edit:
                        sendData(barang, position);
                        break;
                }
                return false;
            });
            popupMenu.show();
        });

    }

    private void sendData(Barang barang, int position) {
        Intent intent = new Intent(mContext, EditActivity.class);
        barang.setPosition(position);
        intent.putExtra("barang",barang);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);

    }

    private void deleteItem(int id,int position, String photo) {
        StringRequest request = new StringRequest(StringRequest.Method.POST, Variable.DELETE_ITEM, response -> {
            MainActivity.barangArrayList.remove(position);
            MainActivity.rvBarang.getAdapter().notifyItemRemoved(position);
            MainActivity.rvBarang.getAdapter().notifyItemRangeChanged(position, barangArrayList.size());
        },error -> {

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("id",id+"");
                map.put("photo",photo);
                return map;
            }
        };
        Volley.newRequestQueue(mContext).add(request);
    }

    @Override
    public int getItemCount() {
        return barangArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvHarga, tvStok;
        ImageView ivPhoto;
        LinearLayout itemLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tv_item_nama);
            tvHarga = itemView.findViewById(R.id.tv_item_harga);
            tvStok = itemView.findViewById(R.id.tv_item_stok);
            ivPhoto = itemView.findViewById(R.id.iv_item_photo);
            itemLayout = itemView.findViewById(R.id.item_layout_barang);
        }
    }
}
