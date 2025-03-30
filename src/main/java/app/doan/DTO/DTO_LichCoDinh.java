package app.doan.DTO;

import java.time.LocalDate;

public class DTO_LichCoDinh extends DTO_LichHoc {
    private LocalDate tgktLap;
    private String maHP;

    public DTO_LichCoDinh(){super();}

    public DTO_LichCoDinh(String maLich, LocalDate tgbd, LocalDate tgkt, String ghiChu, LocalDate tgktLap, String maHP){
        super(maLich, tgbd, tgkt, ghiChu);
        this.tgktLap = tgktLap;
        this.maHP = maHP;
    }

    public void setTgktLap(LocalDate tgktLap) {
        this.tgktLap = tgktLap;
    }

    public LocalDate getTgktLap() {
        return tgktLap;
    }

    public void setMaHP(String maHP) {
        this.maHP = maHP;
    }

    public String getMaHP() {
        return maHP;
    }
}
