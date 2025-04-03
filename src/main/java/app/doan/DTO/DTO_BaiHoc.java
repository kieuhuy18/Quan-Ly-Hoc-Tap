package app.doan.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DTO_BaiHoc implements HienThi{
    private String maBH;
    private String tenBH;
    private String ghiChu;
    private LocalDateTime ngayHoc;
    private boolean trangThai;
    private String maChuong;

    public DTO_BaiHoc(){super();}

    public  DTO_BaiHoc(String maBH, String tenBH, String ghiChu, LocalDateTime ngayHoc, boolean trangThai, String maChuong){
        this.maBH = maBH;
        this.tenBH = tenBH;
        this.ghiChu = ghiChu;
        this.ngayHoc = ngayHoc;
        this.trangThai = trangThai;
        this.maChuong = maChuong;
    }

    public void setMaBH(String maBH) {
        this.maBH = maBH;
    }

    public String getMaBH() {
        return maBH;
    }

    public void setTenBH(String tenBH) {
        this.tenBH = tenBH;
    }

    public String getTenBH() {
        return tenBH;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public boolean getTrangThai() {
        return trangThai;
    }

    public void setNgayHoc(LocalDateTime ngayHoc) {
        this.ngayHoc = ngayHoc;
    }

    public LocalDateTime getNgayHoc() {
        return ngayHoc;
    }

    public void setMaChuong(String maChuong) {
        this.maChuong = maChuong;
    }

    public String getMaChuong() {
        return maChuong;
    }

    @Override
    public String getDisplayName() {
        return tenBH;
    }
}
