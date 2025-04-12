package app.doan.BLL;

import app.doan.DTO.DTO_TaiKhoan;
import app.doan.DAL.DAL_TaiKhoan;

import static app.doan.DAL.DAL_TaiKhoan.tkList;

public class BLL_TaiKhoan {
    DTO_TaiKhoan tk = new DTO_TaiKhoan();
    DAL_TaiKhoan daltk = new DAL_TaiKhoan();

    public boolean tailist(){
        try {
            daltk.getallTKlist();
        }catch(Exception ex){
            System.out.println(ex);
            return false;
        }
        return true;
    }

    public DTO_TaiKhoan DangNhap(String tk, String mk){
        tailist();
        for(DTO_TaiKhoan t: tkList){
            if(t.getEmail().equals(tk) && t.getMatKhau().equals(mk)){
                return t;
            }
        }
        return null;
    }

    public boolean DangKy(String tk, String mk, String tennd){
        tailist();
        for(DTO_TaiKhoan t : tkList){
            if(t.getEmail().equals(tk)){
                return false;
            }
        }
        DTO_TaiKhoan t = new DTO_TaiKhoan(tk, mk, tennd);
        daltk.DangKy(t);
        return true;
    }

    public DTO_TaiKhoan tim(String ma){
        tailist();
        for(DTO_TaiKhoan tk : tkList){
            if(tk.getEmail().equals(ma)){
                return tk;
            }
        }
        return null;
    }

    public boolean sua(DTO_TaiKhoan tk){
        return daltk.suaTK(tk);
    }

    public boolean sua1(DTO_TaiKhoan tk){
        return daltk.sua(tk);
    }
}
