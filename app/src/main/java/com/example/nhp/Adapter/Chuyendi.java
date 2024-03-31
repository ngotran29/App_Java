package com.example.nhp.Adapter;


public class Chuyendi {
    int id;
    String tennhaxe;
    String giodi;
    String gioden;
    String diemdi;
    String diemden;
    int tienve;
    String hinhanh;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTennhaxe() {
        return tennhaxe;
    }

    public void setTennhaxe(String tennhaxe) {
        this.tennhaxe = tennhaxe;
    }

    public String getGiodi() {
        return giodi;
    }

    public void setGiodi(String giodi) {
        this.giodi = giodi;
    }

    public String getGioden() {
        return gioden;
    }

    public void setGioden(String gioden) {
        this.gioden = gioden;
    }

    public String getDiemdi() {
        return diemdi;
    }

    public void setDiemdi(String diemdi) {
        this.diemdi = diemdi;
    }

    public String getDiemden() {
        return diemden;
    }

    public void setDiemden(String diemden) {
        this.diemden = diemden;
    }

    public int getTienve() {
        return tienve;
    }

    public void setTienve(int tienve) {
        this.tienve = tienve;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public Chuyendi() {
    }

    public Chuyendi(int id, String tennhaxe, String giodi, String gioden, String diemdi, String diemden, int tienve, String hinhanh) {
        this.id = id;
        this.tennhaxe = tennhaxe;
        this.giodi = giodi;
        this.gioden = gioden;
        this.diemdi = diemdi;
        this.diemden = diemden;
        this.tienve = tienve;
        this.hinhanh = hinhanh;
    }
}