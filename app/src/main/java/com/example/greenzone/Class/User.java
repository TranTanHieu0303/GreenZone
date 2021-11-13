package com.example.greenzone.Class;

import java.io.Serializable;

public class User implements Serializable {
    String idUser,fullName,sdt,email,password,hinhAnh,gioiTinh,queQuan,anhBia,ngaySinh,token;
    Boolean isOnline;
    Integer soBanBe;
    public User() {
    }

    public User(String idUser, String fullName, String sdt, String email, String password, String hinhAnh, String gioiTinh, String queQuan, String anhBia, String ngaySinh, String token, Boolean isOnline, Integer soBanBe) {
        this.idUser = idUser;
        this.fullName = fullName;
        this.sdt = sdt;
        this.email = email;
        this.password = password;
        this.hinhAnh = hinhAnh;
        this.gioiTinh = gioiTinh;
        this.queQuan = queQuan;
        this.anhBia = anhBia;
        this.ngaySinh = ngaySinh;
        this.token = token;
        this.isOnline = isOnline;
        this.soBanBe = soBanBe;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getQueQuan() {
        return queQuan;
    }

    public void setQueQuan(String queQuan) {
        this.queQuan = queQuan;
    }

    public String getAnhBia() {
        return anhBia;
    }

    public void setAnhBia(String anhBia) {
        this.anhBia = anhBia;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getOnline() {
        return isOnline;
    }

    public void setOnline(Boolean online) {
        isOnline = online;
    }

    public Integer getSoBanBe() {
        return soBanBe;
    }

    public void setSoBanBe(Integer soBanBe) {
        this.soBanBe = soBanBe;
    }
}
