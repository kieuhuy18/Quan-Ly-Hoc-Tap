package app.doan.DTO;

import java.time.LocalDate;

public abstract class DTO_LichHoc {
    private String maLich;
    private LocalDate tgbd;
    private LocalDate tgkt;
    private String ghiChu;

    public DTO_LichHoc(){}

    public DTO_LichHoc(String maLich, LocalDate tgbd, LocalDate tgkt, String ghiChu){
        this.maLich = maLich;
        this.tgbd = tgbd;
        this.tgkt = tgkt;
        this.ghiChu = ghiChu;
    }

    public void setMaLich(String maLich) {
        this.maLich = maLich;
    }

    public String getMaLich() {
        return maLich;
    }

    public void setTgbd(LocalDate tgbd) {
        this.tgbd = tgbd;
    }

    public LocalDate getTgbd() {
        return tgbd;
    }

    public void setTgkt(LocalDate tgkt) {
        this.tgkt = tgkt;
    }

    public LocalDate getTgkt() {
        return tgkt;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getGhiChu() {
        return ghiChu;
    }
}
