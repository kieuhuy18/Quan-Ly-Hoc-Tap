package app.doan.BLL;

import app.doan.DAL.DAL_CongViec;
import app.doan.DTO.DTO_CongViec;

import java.time.LocalDate;
import java.util.ArrayList;

import static app.doan.DAL.DAL_CongViec.cvList;

public class BLL_CongViec {
    public DAL_CongViec dalcv = new DAL_CongViec();
    public static ArrayList<DTO_CongViec> today = new ArrayList<>();
    public static ArrayList<DTO_CongViec> plan = new ArrayList<>();
    public static ArrayList<DTO_CongViec> done = new ArrayList<>();
    public static ArrayList<DTO_CongViec> overdate = new ArrayList<>();

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
        return dalcv.themCV(cv);
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

    public void chialist(){
        today.clear();
        plan.clear();
        done.clear();
        overdate.clear();
        for(DTO_CongViec cv:cvList){
            if(cv.getThoiGian() != null && LocalDate.now().equals(cv.getThoiGian())){
                today.add(cv);
            }if(cv.getThoiGian() != null){
                plan.add(cv);
            }if(cv.getTrangThai()){
                done.add(cv);
            }if(cv.getThoiGian() != null && cv.getThoiGian().isBefore(LocalDate.now())){
                overdate.add(cv);
            }
        }
    }
}
