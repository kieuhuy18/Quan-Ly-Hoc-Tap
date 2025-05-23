package app.doan.BLL;

import app.doan.DAL.DAL_Chuong;
import app.doan.DTO.DTO_Chuong;
import app.doan.DTO.DTO_HocPhan;

import java.util.ArrayList;

import static app.doan.DAL.DAL_Chuong.cList;
import static app.doan.DAL.DAL_HocPhan.hpList;

public class BLL_Chuong {
    DAL_Chuong dalc = new DAL_Chuong();

    public boolean tailist(){
        try {
            ArrayList<DTO_Chuong> temp = new ArrayList<>();
            dalc.getallClist();
            for(DTO_HocPhan hp:hpList){
                for(DTO_Chuong c: cList){
                    if (c.getMaHP().equals(hp.getMaHP())){
                        temp.add(c);
                    }
                }
            }
            cList = temp;
        }catch(Exception ex){
            System.out.println(ex);
            return false;
        }
        return true;
    }

    public DTO_Chuong tim(String mac){
        tailist();
        for(DTO_Chuong c : cList){
            if(c.getMaChuong().equals(mac)){
                return c;
            }
        }
        return null;
    }

    public boolean them1(DTO_Chuong c){
        return dalc.themC(c);
    }

    public String tangma() {
        dalc.getallClist();
        int maxNumber = 0;

        if (cList.isEmpty()) {
            return "c1";
        }

        for (DTO_Chuong c : cList) {
            String ma = c.getMaChuong();

            if (ma != null && ma.startsWith("c") && ma.length() > 1) {
                String soStr = ma.substring(1); // lấy phần số sau "c"
                try {
                    int number = Integer.parseInt(soStr);
                    if (number > maxNumber) {
                        maxNumber = number;
                    }
                } catch (NumberFormatException e) {
                    System.out.println(e);
                }
            }
        }

        return "c" + (maxNumber + 1);
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

    public boolean sua(DTO_Chuong c){return dalc.suaC(c);}

    public boolean xoa(DTO_Chuong c){
        return dalc.xoaC(c);
    }
}
