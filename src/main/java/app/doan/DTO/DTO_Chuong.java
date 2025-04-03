package app.doan.DTO;

public class DTO_Chuong implements HienThi{
    private String maChuong;
    private String tenChuong;
    private String moTa;
    private boolean trangThai;
    private String maHP;

    public DTO_Chuong(){}

    public DTO_Chuong (String maChuong, String tenChuong, String moTa, boolean trangThai, String maHP){
        this.maChuong = maChuong;
        this.tenChuong = tenChuong;
        this.moTa = moTa;
        this.trangThai = trangThai;
        this.maHP = maHP;
    }

    public void setMaChuong(String maChuong) {
        this.maChuong = maChuong;
    }

    public String getMaChuong() {
        return maChuong;
    }

    public void setTenChuong(String tenChuong) {
        this.tenChuong = tenChuong;
    }

    public String getTenChuong() {
        return tenChuong;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public boolean getTrangThai() {
        return trangThai;
    }

    public void setMaHP(String maHP) {
        this.maHP = maHP;
    }

    public String getMaHP() {
        return maHP;
    }

    @Override
    public String getDisplayName() {
        return tenChuong;
    }
}
