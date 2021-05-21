package com.example.phamtuananhkiemtra2bai2;

public class KhoaHoc {

    private String id;
    private String ten;
    private String ngayBatDau;
    private String linhVuc;
    private String active;

    public KhoaHoc(String id, String ten, String ngayBatDau, String linhVuc, String active) {
        this.id = id;
        this.ten = ten;
        this.ngayBatDau = ngayBatDau;
        this.linhVuc = linhVuc;
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return ten;
    }

    public void setName(String name) {
        this.ten = name;
    }

    public String getDate() {
        return ngayBatDau;
    }

    public void setDate(String date) {
        this.ngayBatDau = date;
    }

    public String getMajor() {
        return linhVuc;
    }

    public void setMajor(String major) {
        this.linhVuc = major;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}

