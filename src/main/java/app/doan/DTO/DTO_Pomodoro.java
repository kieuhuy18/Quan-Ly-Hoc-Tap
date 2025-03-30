package app.doan.DTO;

public class DTO_Pomodoro {
    private int thoiGian1pommo;
    private int thoiGianNghiNgan;
    private int thoiGianNghiDai;
    private int giaiLaoSau;
    private String maND;


    public DTO_Pomodoro(){}

    public DTO_Pomodoro(int thoiGian1pommo, int thoiGianNghiNgan, int thoiGianNghiDai, int giaiLaoSau, String maND){
        this.thoiGian1pommo = thoiGian1pommo;
        this.thoiGianNghiNgan = thoiGianNghiNgan;
        this.thoiGianNghiDai = thoiGianNghiDai;
        this.giaiLaoSau = giaiLaoSau;
        this.maND = maND;
    }

    public void setThoiGian1pommo(int thoiGian1pommo) {
        this.thoiGian1pommo = thoiGian1pommo;
    }

    public int getThoiGian1pommo() {
        return thoiGian1pommo;
    }

    public void setThoiGianNghiDai(int thoiGianNghiDai) {
        this.thoiGianNghiDai = thoiGianNghiDai;
    }

    public int getThoiGianNghiDai() {
        return thoiGianNghiDai;
    }

    public void setGiaiLaoSau(int giaiLaoSau) {
        this.giaiLaoSau = giaiLaoSau;
    }

    public int getGiaiLaoSau() {
        return giaiLaoSau;
    }

    public void setThoiGianNghiNgan(int thoiGianNghiNgan) {
        this.thoiGianNghiNgan = thoiGianNghiNgan;
    }

    public int getThoiGianNghiNgan() {
        return thoiGianNghiNgan;
    }

    public void setMaND(String maND) {
        this.maND = maND;
    }

    public String getMaND() {
        return maND;
    }
}
