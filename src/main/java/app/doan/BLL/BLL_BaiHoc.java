package app.doan.BLL;

import app.doan.DAL.DAL_BaiHoc;
import app.doan.DTO.DTO_BaiHoc;
import app.doan.DTO.DTO_Chuong;
import eu.hansolo.tilesfx.addons.Switch;

import java.util.ArrayList;

import static app.doan.DAL.DAL_BaiHoc.bhList;
import static app.doan.DAL.DAL_Chuong.cList;

public class BLL_BaiHoc {
    DAL_BaiHoc dalbh = new DAL_BaiHoc();
    public static ArrayList<String> tenBHList = new ArrayList<>();

    public boolean tailist(){
        try {
            ArrayList<DTO_BaiHoc> temp = new ArrayList<>();
            dalbh.getallBHlist();
            for(DTO_Chuong c : cList){
                for(DTO_BaiHoc bh:bhList){
                    if(bh.getMaChuong().equals(c.getMaChuong())){
                        temp.add(bh);
                        tenBHList.add(bh.getTenBH());
                    }
                }
            }
            bhList = temp;
        }catch(Exception ex){
            System.out.println(ex);
            return false;
        }
        return true;
    }

    public boolean them1(DTO_BaiHoc jbh){
        return dalbh.themBH(jbh);
    }

    public String tangma(){
        dalbh.getallBHlist();
        int maxNumber = 0;

        if(bhList.isEmpty()){
            return "bh" + 1;
        }

        for(DTO_BaiHoc bh : bhList){
            if (bh.getMaBH().matches("bh\\d+")) {
                int number = Integer.parseInt(bh.getMaBH().substring(2));
                if (number > maxNumber) {
                    maxNumber = number;
                }
            }
        }
        maxNumber  = maxNumber + 1;
        return "bh" + maxNumber;
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

    public Boolean sua(DTO_BaiHoc bh){
        return dalbh.suaBH(bh);
    }

    public DTO_BaiHoc tim(String ma){
        tailist();
        for(DTO_BaiHoc b : bhList){
            if(b.getMaBH().equals(ma)){
                return b;
            }
        }
        return null;
    }

    public String timBH(int i, String nd){
        switch(i){
            case 1:
                for(DTO_BaiHoc b:bhList){
                    if(b.getMaBH().equals(nd)){
                        return b.getTenBH();
                    }
                }
                break;

            case 2:
                int dem = 0;
                String kq = "";
                for(DTO_BaiHoc b:bhList){
                    if(b.getTenBH().equals(nd)){
                        dem++;
                        kq = b.getMaBH();
                    }
                }
                if(dem == 1){
                    return kq;
                }else{
                    return "trung";
                }
        }
        return null;
    }

    public boolean xoa(DTO_BaiHoc bh){
        return dalbh.xoaBH(bh);
    }
}
