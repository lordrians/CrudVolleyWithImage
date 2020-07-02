package com.example.crudvolley;

import android.os.Parcel;
import android.os.Parcelable;

public class Barang implements Parcelable {

    private int id, harga, stok, position;
    private String nama, photo;

    public Barang() {
    }

    protected Barang(Parcel in) {
        id = in.readInt();
        harga = in.readInt();
        stok = in.readInt();
        position = in.readInt();
        nama = in.readString();
        photo = in.readString();
    }

    public static final Creator<Barang> CREATOR = new Creator<Barang>() {
        @Override
        public Barang createFromParcel(Parcel in) {
            return new Barang(in);
        }

        @Override
        public Barang[] newArray(int size) {
            return new Barang[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(harga);
        dest.writeInt(stok);
        dest.writeInt(position);
        dest.writeString(nama);
        dest.writeString(photo);
    }
}
