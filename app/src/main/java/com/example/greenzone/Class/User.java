package com.example.greenzone.Class;

import java.io.Serializable;

public class User implements Serializable {
    String idUser,Ho,Ten,SDT,Email,Password,HinhAnh,GioiTinh,NgaySinh,token,TrangThai,QueQuan,AnhBia;

    public User() {
    }

    public User(String idUser, String ho, String ten, String SDT, String email, String password, String hinhAnh, String gioiTinh, String ngaySinh, String token, String trangThai, String queQuan, String anhBia) {
        this.idUser = idUser;
        Ho = ho;
        Ten = ten;
        this.SDT = SDT;
        Email = email;
        Password = password;
        HinhAnh = hinhAnh;
        GioiTinh = gioiTinh;
        NgaySinh = ngaySinh;
        this.token = token;
        TrangThai = trangThai;
        QueQuan = queQuan;
        AnhBia = anhBia;
    }
    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getHo() {
        return Ho;
    }

    public void setHo(String ho) {
        Ho = ho;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        HinhAnh = hinhAnh;
    }

    public String getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        GioiTinh = gioiTinh;
    }

    public String getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        NgaySinh = ngaySinh;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String trangThai) {
        TrangThai = trangThai;
    }

    public String getQueQuan() {
        return QueQuan;
    }

    public void setQueQuan(String queQuan) {
        QueQuan = queQuan;
    }

    public String getAnhBia() {
        return AnhBia;
    }

    public void setAnhBia(String anhBia) {
        AnhBia = anhBia;
    }
}
