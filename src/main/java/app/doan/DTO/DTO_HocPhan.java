package app.doan.DTO;

public class DTO_HocPhan {
    private String maHP;
    private String tenHP;
    private String giangVien;
    private String moTa;
    private boolean trangThai;
    private String maTK;
    private boolean lapLai;

    public DTO_HocPhan(){}

    public DTO_HocPhan(String maHP, String tenHP, String giangVien, Boolean LapLai, String moTa, Boolean lapLai, String maTK){
        this.maHP = maHP;
        this.tenHP = tenHP;
        this.giangVien = giangVien;
        this.moTa = moTa;
        this.maTK = maTK;
        this.lapLai = lapLai;
    }

    public void setMaHP(String maHP) {
        this.maHP = maHP;
    }

    public String getMaHP() {
        return maHP;
    }

    public void setTenHP(String tenHP) {
        this.tenHP = tenHP;
    }

    public String getTenHP() {
        return tenHP;
    }

    public void setGiangVien(String giangVien) {
        this.giangVien = giangVien;
    }

    public String getGiangVien() {
        return giangVien;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMaTK(String maTK) {
        this.maTK = maTK;
    }

    public String getMaTK() {
        return maTK;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public boolean getTrangThai() {
        return trangThai;
    }

    public void setLapLai(boolean lapLai) {
        this.lapLai = lapLai;
    }

    public boolean getLapLai() {
        return lapLai;
    }


}
