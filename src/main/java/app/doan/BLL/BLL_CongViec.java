package app.doan.BLL;

import app.doan.DAL.DAL_CongViec;
import app.doan.DTO.DTO_CongViec;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static app.doan.DAL.DAL_CongViec.cvList;
import static app.doan.DAL.DAL_CongViec.cvndList;

public class BLL_CongViec {
    public DAL_CongViec dalcv = new DAL_CongViec();
    public static ArrayList<DTO_CongViec> today = new ArrayList<>();
    public static ArrayList<DTO_CongViec> plan = new ArrayList<>();
    public static ArrayList<DTO_CongViec> done = new ArrayList<>();
    public static ArrayList<DTO_CongViec> overdate = new ArrayList<>();
    public static int ht = 0;
    public static int cht = 0;

    public boolean tailist(){
        try {
            ArrayList<DTO_CongViec> temp = new ArrayList<DTO_CongViec>();
            dalcv.getallNDCVlist();
            Set<String> maSet = new HashSet<>(cvndList);
            for (DTO_CongViec cv : dalcv.getallCVlist()) {
                if (maSet.contains(cv.getMaCV())) {
                    temp.add(cv);
                }
            }
            cvList = temp;
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
        tailist();
        for(DTO_CongViec cv:cvList){
            if(cv.getThoiGian() != null && LocalDate.now().equals(cv.getThoiGian())){
                today.add(cv);
            }if(cv.getThoiGian() != null && !cv.getTrangThai()){
                plan.add(cv);
            }if(cv.getTrangThai()){
                done.add(cv);
            }if(cv.getThoiGian() != null && cv.getThoiGian().isBefore(LocalDate.now()) && !cv.getTrangThai()){
                overdate.add(cv);
            }
        }
    }

    public void trangchuCV(){
        ht = 0;
        cht = 0;
        chialist();
        for(DTO_CongViec cv:today){
            if(cv.getTrangThai()){
                ht = ht + 1;
            }else{
                cht = cht + 1;
            }
        }
    }

    public DTO_CongViec tim(String ma){
        tailist();
        for(DTO_CongViec cv : cvList){
            if(Objects.equals(cv.getMaCV(), ma)){
                return cv;
            }
        }
        return null;
    }

    public boolean sua(DTO_CongViec cv){
        return dalcv.suaCV(cv);
    }

    public String thongke(int i){
        chialist();
        int tk = 0;
        switch (i){
            case 1:
                for(DTO_CongViec cv: cvList){
                    tk = tk + (cv.getPomoTT() * 25);
                }
                break;

            case 2:
                for(DTO_CongViec cv: today){
                    tk = tk + (cv.getPomoTT() * 25);
                }
                break;

            case 3:
                for(DTO_CongViec cv: cvList){
                    if(cv.getTrangThai()){
                        tk = tk + 1;
                    }
                }
                break;

            case 4:
                for(DTO_CongViec cv: today){
                    if(cv.getTrangThai()){
                        tk = tk + 1;
                    }
                }
                break;

            case 5:
                for(DTO_CongViec cv: cvList){
                    tk = tk + cv.getPomoTT();
                }
                break;

            case 6:
                for(DTO_CongViec cv: today){
                    tk = tk + cv.getPomoTT();
                }
                break;

            default:
                System.out.println("Loi!");
        }
        return String.valueOf(tk);
    }
}
