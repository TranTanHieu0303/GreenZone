package com.example.greenzone.Class;

import com.google.type.DateTime;

import java.io.Serializable;
import java.time.LocalDateTime;

public class BaiViet implements Serializable {
    String idBaiViet,idUser,noidung,hinhAnh,video,cheDo;
    Integer luotLike,luotComment,luotShare;
    String ngayDang;

    public BaiViet() {
    }

    public BaiViet(String idBaiViet, String idUser, String noidung, String hinhAnh, String video, String cheDo, Integer luotLike, Integer luotComment, Integer luotShare, String ngayDang) {
        this.idBaiViet = idBaiViet;
        this.idUser = idUser;
        this.noidung = noidung;
        this.hinhAnh = hinhAnh;
        this.video = video;
        this.cheDo = cheDo;
        this.luotLike = luotLike;
        this.luotComment = luotComment;
        this.luotShare = luotShare;
        this.ngayDang = ngayDang;
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

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getCheDo() {
        return cheDo;
    }

    public void setCheDo(String cheDo) {
        this.cheDo = cheDo;
    }

    public Integer getLuotLike() {
        return luotLike;
    }

    public void setLuotLike(Integer luotLike) {
        this.luotLike = luotLike;
    }

    public Integer getLuotComment() {
        return luotComment;
    }

    public void setLuotComment(Integer luotComment) {
        this.luotComment = luotComment;
    }

    public Integer getLuotShare() {
        return luotShare;
    }

    public void setLuotShare(Integer luotShare) {
        this.luotShare = luotShare;
    }

    public String getNgayDang() {
        return ngayDang;
    }

    public void setNgayDang(String ngayDang) {
        this.ngayDang = ngayDang;
    }
}
