package app.doan.DAL;

import app.doan.DTO.DTO_LichCoDinh;

import java.sql.*;
import java.util.ArrayList;

import static app.doan.DAL.DatabaseConnection.close;

public class DAL_LichCoDinh {
    public static Connection conn;
    public Statement stm = null;
    public static PreparedStatement p = null;
    public static ArrayList<DTO_LichCoDinh> hpList = new ArrayList<DTO_LichCoDinh>();

//    public ArrayList<DTO_LichCoDinh> getallHPlist(){
//        try{
//            hpList.clear();
//            String sql = "SELECT * FROM LichCoDinh";
//            Connection conn = app.doan.DAL.DatabaseConnection.connect();
//            assert conn != null;
//            stm = conn.createStatement();
//            ResultSet rs = stm.executeQuery(sql);
//            while(rs.next()){
//                DTO_LichCoDinh tk = new DTO_LichCoDinh();
//                tk.setMaLich(rs.getString("MaLich"));
//                tk.setThoi(rs.getString("TenHocPhan"));
//                tk.setGiangVien(rs.getString("GiangVien"));
//                tk.setLapLai(rs.getBoolean("LapLai"));
//                tk.setMoTa(rs.getString("MoTa"));
//                tk.setTrangThai(rs.getBoolean("TrangThai"));
//                tk.setMaTK(rs.getString("MaTaiKhoan"));
//                hpList.add(tk);
//            }
//        }catch(SQLException ex){
//            System.out.println(ex);
//        }finally{
//            close();
//        }
//        return hpList;
//    }
}
