package com.example.greenzone.Class;

public class GroupPage {
    String idGoup,goupName,idUserHost,anhDaiDien,anhBia,ngayLap,gioiThieu;

    public GroupPage() {
    }

    public GroupPage(String idGoup, String goupName, String idUserHost, String anhDaiDien, String anhBia, String ngayLap, String gioiThieu) {
        this.idGoup = idGoup;
        this.goupName = goupName;
        this.idUserHost = idUserHost;
        this.anhDaiDien = anhDaiDien;
        this.anhBia = anhBia;
        this.ngayLap = ngayLap;
        this.gioiThieu = gioiThieu;
    }

    public String getIdGoup() {
        return idGoup;
    }

    public void setIdGoup(String idGoup) {
        this.idGoup = idGoup;
    }

    public String getGoupName() {
        return goupName;
    }

    public void setGoupName(String goupName) {
        this.goupName = goupName;
    }

    public String getIdUserHost() {
        return idUserHost;
    }

    public void setIdUserHost(String idUserHost) {
        this.idUserHost = idUserHost;
    }

    public String getAnhDaiDien() {
        return anhDaiDien;
    }

    public void setAnhDaiDien(String anhDaiDien) {
        this.anhDaiDien = anhDaiDien;
    }

    public String getAnhBia() {
        return anhBia;
    }

    public void setAnhBia(String anhBia) {
        this.anhBia = anhBia;
    }

    public String getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(String ngayLap) {
        this.ngayLap = ngayLap;
    }

    public String getGioiThieu() {
        return gioiThieu;
    }

    public void setGioiThieu(String gioiThieu) {
        this.gioiThieu = gioiThieu;
    }
}
