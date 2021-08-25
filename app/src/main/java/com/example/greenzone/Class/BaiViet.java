package com.example.greenzone.Class;

public class BaiViet {
    String idBaiViet,idUser,NoiDung,HinhAnh,Video,CheDO,Luotlike,Luotcomment,LuotShare;

    public BaiViet() {
    }

    public BaiViet(String idBaiViet, String idUser, String noiDung, String hinhAnh, String video, String cheDO, String luotlike, String luotcomment, String luotShare) {
        this.idBaiViet = idBaiViet;
        this.idUser = idUser;
        NoiDung = noiDung;
        HinhAnh = hinhAnh;
        Video = video;
        CheDO = cheDO;
        Luotlike = luotlike;
        Luotcomment = luotcomment;
        LuotShare = luotShare;
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
        return NoiDung;
    }

    public void setNoiDung(String noiDung) {
        NoiDung = noiDung;
    }

    public String getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        HinhAnh = hinhAnh;
    }

    public String getVideo() {
        return Video;
    }

    public void setVideo(String video) {
        Video = video;
    }

    public String getCheDO() {
        return CheDO;
    }

    public void setCheDO(String cheDO) {
        CheDO = cheDO;
    }

    public String getLuotlike() {
        return Luotlike;
    }

    public void setLuotlike(String luotlike) {
        Luotlike = luotlike;
    }

    public String getLuotcomment() {
        return Luotcomment;
    }

    public void setLuotcomment(String luotcomment) {
        Luotcomment = luotcomment;
    }

    public String getLuotShare() {
        return LuotShare;
    }

    public void setLuotShare(String luotShare) {
        LuotShare = luotShare;
    }
}
