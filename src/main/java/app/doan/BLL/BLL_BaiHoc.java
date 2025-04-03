package app.doan.BLL;

import app.doan.DAL.DAL_BaiHoc;
import app.doan.DTO.DTO_BaiHoc;

import java.util.ArrayList;

import static app.doan.DAL.DAL_BaiHoc.bhList;

public class BLL_BaiHoc {
    DAL_BaiHoc dalc = new DAL_BaiHoc();

    public boolean tailist(){
        try {
            dalc.getallBHlist();
        }catch(Exception ex){
            System.out.println(ex);
            return false;
        }
        return true;
    }

    public boolean them1(DTO_BaiHoc jbh){
        return dalc.themC(jbh);
    }

    public String tangma(){
        dalc.getallBHlist();
        int maxNumber = 0;

        if(bhList.isEmpty()){
            return "jbh" + 1;
        }

        for(DTO_BaiHoc bh : bhList){
            if (bh.getMaBH().matches("c\\d+")) {
                int number = Integer.parseInt(bh.getMaBH().substring(2));
                if (number > maxNumber) {
                    maxNumber = number;
                }
            }
        }
        maxNumber  = maxNumber + 1;
        return "jbh" + maxNumber;
    }

    public ArrayList<DTO_BaiHoc> timtheochuong(String mahp){
        ArrayList<DTO_BaiHoc> chp = new ArrayList<>();
        for(DTO_BaiHoc jbh: bhList){
            if(jbh.getMaChuong().equals(mahp)){
                chp.add(jbh);
            }
        }
        return chp;
    }
}
