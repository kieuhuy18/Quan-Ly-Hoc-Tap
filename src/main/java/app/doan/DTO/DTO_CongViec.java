package app.doan.DTO;

import java.time.LocalDate;
import java.time.LocalTime;

public class DTO_CongViec {
    private String maCV;
    private String tenCV;
    private LocalDate thoiGian;
    private String ghiChu;
    private int pomoUT;
    private int pomoTT;
    private boolean trangThai;
    private int doUuTien;
    private LocalTime thoiGianBatDau;
    private String maBH;

    public DTO_CongViec(){}

    public DTO_CongViec(String ma, String ten, LocalDate date, String des, int dut)
    {
        this.maCV = ma;
        this.tenCV = ten;
        this.thoiGian = date;
        this.ghiChu = des;
        this.trangThai = false;
        this.doUuTien = dut;
    }

    public DTO_CongViec(String maCV, String tenCV, String ghiChu, LocalDate thoiGian, int pomoUT, int pomoTT, boolean trangThai, int doUuTien, LocalTime thoiGianBatDau, String maBH){
        this.maCV = maCV;
        this.tenCV = tenCV;
        this.thoiGian = thoiGian;
        this.ghiChu = ghiChu;
        this.pomoTT = pomoTT;
        this.pomoUT = pomoUT;
        this.trangThai = trangThai;
        this.doUuTien = doUuTien;
        this.thoiGianBatDau = thoiGianBatDau;
        this.maBH = maBH;
    }

    public void setMaCV(String maCV) {
        this.maCV = maCV;
    }

    public String getMaCV() {
        return maCV;
    }

    public void setTenCV(String tenCV) {
        this.tenCV = tenCV;
    }

    public String getTenCV() {
        return tenCV;
    }

    public void setThoiGian(LocalDate thoiGian) {
        this.thoiGian = thoiGian;
    }

    public LocalDate getThoiGian() {
        return thoiGian;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setPomoTT(int pomoTT) {
        this.pomoTT = pomoTT;
    }

    public int getPomoTT() {
        return pomoTT;
    }

    public void setPomoUT(int pomoUT) {
        this.pomoUT = pomoUT;
    }

    public int getPomoUT() {
        return pomoUT;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public Boolean getTrangThai() {
        return trangThai;
    }

    public void setDoUuTien(int doUuTien) {
        this.doUuTien = doUuTien;
    }

    public int getDoUuTien() {
        return doUuTien;
    }

    public void setThoiGianBatDau(LocalTime thoiGianBatDau) {
        this.thoiGianBatDau = thoiGianBatDau;
    }

    public LocalTime getThoiGianBatDau() {
        return thoiGianBatDau;
    }

    public void setMaBH(String maBH) {
        this.maBH = maBH;
    }

    public String getMaBH() {
        return maBH;
    }
}
