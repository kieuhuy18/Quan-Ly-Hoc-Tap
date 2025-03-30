package app.doan.DAL;

import app.doan.DTO.DTO_Pomodoro;

import java.sql.*;
import java.util.ArrayList;

import static app.doan.DAL.DatabaseConnection.close;

public class DAL_Pomodoro {
    public static Connection conn;
    public Statement stm = null;
    public static PreparedStatement p = null;

    public boolean themPM(DTO_Pomodoro pm){
        boolean result = false;
        try{
            String sql = "INSERT INTO Pomodoro VALUES(?, ?, ?, ?, ?)";
            conn = app.doan.DAL.DatabaseConnection.connect();
            p = conn.prepareStatement(sql);
            p.setInt(1, pm.getThoiGian1pommo());
            p.setInt(2, pm.getThoiGianNghiNgan());
            p.setInt(3, pm.getThoiGianNghiDai());
            p.setInt(4, pm.getGiaiLaoSau());
            p.setString(5, pm.getMaND());
            if(p.executeUpdate() >= 1){
                result = true;
            }
        }catch(SQLException ex){
            System.out.println(ex);
        }finally{
            close();
        }
        return result;
    }

    public boolean suaPM(DTO_Pomodoro pm){
        boolean result = false;
        try{
            String SQL = "UPDATE Pomodoro SET ThoiGian1pomo = ?, ThoiGianNghiNgan = ?, ThoiGianNghiDai = ?, GiaiLaoSau = ?, MaTaiKhoan = ? WHERE MaTaiKhoan = ? ";
            conn = app.doan.DAL.DatabaseConnection.connect();
            p = conn.prepareStatement(SQL);
            p.setInt(1, pm.getThoiGian1pommo());
            p.setInt(2, pm.getThoiGianNghiNgan());
            p.setInt(3, pm.getThoiGianNghiDai());
            p.setInt(4, pm.getGiaiLaoSau());
            p.setString(5, pm.getMaND());
            p.executeUpdate();
            if(p.executeUpdate() >= 1){
                result = true;
            }
        }catch(SQLException ex){
            System.out.println(ex);
        }finally{
            close();
        }
        return result;
    }
}
