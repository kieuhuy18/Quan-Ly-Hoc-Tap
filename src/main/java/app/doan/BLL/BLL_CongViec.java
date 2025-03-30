package app.doan.BLL;

import app.doan.DAL.DAL_CongViec;
import app.doan.DTO.DTO_CongViec;

import static app.doan.DAL.DAL_CongViec.cvList;

public class BLL_CongViec {
    public DAL_CongViec dalcv = new DAL_CongViec();

    public boolean tailist(){
        try {
            dalcv.getallCVlist();
        }catch(Exception ex){
            System.out.println(ex);
            return false;
        }
        return true;
    }

    public boolean them1(DTO_CongViec cv){
        if(dalcv.themCV(cv)){
            return true;
        }
        return false;
    }

    public String tangma(){
        dalcv.getallCVlist();
        int maxNumber = 0;

        if(cvList.isEmpty()){
            return "cv" + 1;
        }

        for(DTO_CongViec cv : cvList){
            if (cv.getMaCV().matches("cv\\d+")) { // Kiểm tra định dạng "cvX"
                int number = Integer.parseInt(cv.getMaCV().substring(2)); // Lấy số từ "cvX"
                if (number > maxNumber) {
                    maxNumber = number;
                }
            }
        }
        maxNumber  = maxNumber + 1;

        return "cv" + maxNumber;
    }
}
