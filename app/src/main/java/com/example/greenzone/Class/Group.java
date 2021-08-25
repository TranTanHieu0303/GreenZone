package com.example.greenzone.Class;

import java.io.Serializable;

public class Group implements Serializable {
    String idGroup,TenGroup,idMesCuoi,timeMesCuoi;
    int SothanhVien;
    public Group() {
    }
    public Group(String idGroup, String tenGroup, String idMesCuoi, String timeMesCuoi, int sothanhVien) {
        this.idGroup = idGroup;
        TenGroup = tenGroup;
        this.idMesCuoi = idMesCuoi;
        this.timeMesCuoi = timeMesCuoi;
        SothanhVien = sothanhVien;
    }

    public int getSothanhVien() {
        return SothanhVien;
    }

    public void setSothanhVien(int sothanhVien) {
        SothanhVien = sothanhVien;
    }

    public String getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(String idGroup) {
        this.idGroup = idGroup;
    }

    public String getTenGroup() {
        return TenGroup;
    }

    public void setTenGroup(String tenGroup) {
        TenGroup = tenGroup;
    }

    public String getIdMesCuoi() {
        return idMesCuoi;
    }

    public void setIdMesCuoi(String idMesCuoi) {
        this.idMesCuoi = idMesCuoi;
    }

    public String getTimeMesCuoi() {
        return timeMesCuoi;
    }

    public void setTimeMesCuoi(String timeMesCuoi) {
        this.timeMesCuoi = timeMesCuoi;
    }
}
