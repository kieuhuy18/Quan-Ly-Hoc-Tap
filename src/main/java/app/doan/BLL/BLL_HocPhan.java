package app.doan.BLL;

import app.doan.DAL.DAL_HocPhan;
import app.doan.DTO.DTO_HocPhan;

import static app.doan.DAL.DAL_HocPhan.hpList;

public class BLL_HocPhan {
    DAL_HocPhan dalhp = new DAL_HocPhan();

    public boolean tailist(){
        try {
            dalhp.getallHPlist();
        }catch(Exception ex){
            System.out.println(ex);
            return false;
        }
        return true;
    }

    public boolean them1(DTO_HocPhan hp){
        return dalhp.themHP(hp);
    }

    public String tangma(){
        dalhp.getallHPlist();
        int maxNumber = 0;

        if(hpList.isEmpty()){
            return "hp" + 1;
        }

        for(DTO_HocPhan hp : hpList){
            if (hp.getMaHP().matches("hp\\d+")) { // Kiểm tra định dạng "hpX"
                int number = Integer.parseInt(hp.getMaHP().substring(2)); // Lấy số từ "hpX"
                if (number > maxNumber) {
                    maxNumber = number;
                }
            }
        }
        maxNumber  = maxNumber + 1;
        return "hp" + maxNumber;
    }

    public DTO_HocPhan tim(String ma){
        for(DTO_HocPhan hp:hpList){
            if(hp.getMaHP().equals(ma)){
                return hp;
            }
        }
        return null;
    }

    public Boolean sua(DTO_HocPhan hp){
        return dalhp.suaHP(hp);
    }
}
