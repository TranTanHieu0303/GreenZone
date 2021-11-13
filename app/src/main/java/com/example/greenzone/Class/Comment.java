package com.example.greenzone.Class;

import java.io.Serializable;
import java.util.List;

public class Comment implements Serializable {
    String idCmt,idBaiViet,idUser,noiDung,thoiGian,traloiCmt;
    List<Comment> inverseTraloiCmtNavigation;

    public Comment() {
    }

    public Comment(String idCmt, String idBaiViet, String idUser, String noiDung, String thoiGian, String traloiCmt) {
        this.idCmt = idCmt;
        this.idBaiViet = idBaiViet;
        this.idUser = idUser;
        this.noiDung = noiDung;
        this.thoiGian = thoiGian;
        this.traloiCmt = traloiCmt;
    }

    public Comment(String idCmt, String idBaiViet, String idUser, String noiDung, String thoiGian, String traloiCmt, List<Comment> inverseTraloiCmtNavigation) {
        this.idCmt = idCmt;
        this.idBaiViet = idBaiViet;
        this.idUser = idUser;
        this.noiDung = noiDung;
        this.thoiGian = thoiGian;
        this.traloiCmt = traloiCmt;
        this.inverseTraloiCmtNavigation = inverseTraloiCmtNavigation;
    }

    public String getIdCmt() {
        return idCmt;
    }

    public void setIdCmt(String idCmt) {
        this.idCmt = idCmt;
    }

    public String getIdBaiViet() {
        return idBaiViet;
    }

    public void setIdBaiViet(String idBaiViet) {
        this.idBaiViet = idBaiViet;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getTraloiCmt() {
        return traloiCmt;
    }

    public void setTraloiCmt(String traloiCmt) {
        this.traloiCmt = traloiCmt;
    }

    public List<Comment> getInverseTraloiCmtNavigation() {
        return inverseTraloiCmtNavigation;
    }

    public void setInverseTraloiCmtNavigation(List<Comment> inverseTraloiCmtNavigation) {
        this.inverseTraloiCmtNavigation = inverseTraloiCmtNavigation;
    }
}
