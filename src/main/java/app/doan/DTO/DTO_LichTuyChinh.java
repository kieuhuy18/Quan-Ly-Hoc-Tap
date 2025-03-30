package app.doan.DTO;

import java.time.LocalDate;

public class DTO_LichTuyChinh extends DTO_LichHoc{
    private String maBH;

    public DTO_LichTuyChinh(){super();}

    public DTO_LichTuyChinh(String maLich, LocalDate tgbd, LocalDate tgkt, String ghiChu, String maBH){
        super(maLich, tgbd, tgkt, ghiChu);
        this.maBH = maBH;
    }

    public void setMaBH(String maBH) {
        this.maBH = maBH;
    }

    public String getMaBH() {
        return maBH;
    }
}
