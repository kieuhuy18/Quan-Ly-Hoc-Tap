package app.doan.BLL;

import app.doan.DAL.DAL_Chuong;
import app.doan.DTO.DTO_Chuong;

import java.util.ArrayList;

import static app.doan.DAL.DAL_Chuong.cList;

public class BLL_Chuong {
    DAL_Chuong dalc = new DAL_Chuong();

    public boolean tailist(){
        try {
            dalc.getallClist();
        }catch(Exception ex){
            System.out.println(ex);
            return false;
        }
        return true;
    }

    public boolean them1(DTO_Chuong c){
        return dalc.themC(c);
    }

    public String tangma(){
        dalc.getallClist();
        int maxNumber = 0;

        if(cList.isEmpty()){
            return "c" + 1;
        }

        for(DTO_Chuong c : cList){
            if (c.getMaChuong().matches("c\\d+")) { // Kiểm tra định dạng "hpX"
                int number = Integer.parseInt(c.getMaChuong().substring(2)); // Lấy số từ "hpX"
                if (number > maxNumber) {
                    maxNumber = number;
                }
            }
        }
        maxNumber  = maxNumber + 1;
        return "c" + maxNumber;
    }

    public ArrayList<DTO_Chuong> timtheohocphan(String mahp){
        ArrayList<DTO_Chuong> chp = new ArrayList<>();
        for(DTO_Chuong c: cList){
            if(c.getMaHP().equals(mahp)){
                chp.add(c);
            }
        }
        return chp;
    }
}
