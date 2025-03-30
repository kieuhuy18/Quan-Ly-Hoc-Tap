package app.doan.DTO;

public class DTO_TaiKhoan {
    private String tenND;
    private String email;
    private String matKhau;

    public DTO_TaiKhoan(){}

    public DTO_TaiKhoan(String tenND, String email, String matKhau){
        this.tenND = tenND;
        this.email = email;
        this.matKhau = matKhau;
    }

    public void setTenND(String tenND) {
        this.tenND = tenND;
    }

    public String getTenND() {
        return tenND;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
