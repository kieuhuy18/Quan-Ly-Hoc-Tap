package app.doan.DAL;

import app.doan.DTO.DTO_CongViec;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

import static app.doan.DAL.DatabaseConnection.close;

public class DAL_CongViec {
    public static Connection conn;
    public Statement stm = null;
    public static PreparedStatement p = null;
    public static ArrayList<DTO_CongViec> cvList = new ArrayList<DTO_CongViec>();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("HH:mm");

    public ArrayList<DTO_CongViec> getallCVlist(){
        try{
            cvList.clear();
            String sql = "SELECT * FROM CongViec";
            Connection conn = app.doan.DAL.DatabaseConnection.connect();
            assert conn != null;
            stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while(rs.next()){
                DTO_CongViec cv = new DTO_CongViec();
                cv.setMaCV(rs.getString("MaCongViec"));
                cv.setTenCV(rs.getString("TenCongViec"));
                if("Trống".equals(rs.getString("ThoiGian"))){
                    cv.setThoiGian(null);
                }else {
                    LocalDate date = LocalDate.parse(rs.getString("ThoiGian"), formatter);
                    cv.setThoiGian(date);
                }
                cv.setGhiChu(rs.getString("GhiChu"));
                cv.setPomoUT(rs.getInt("pomoUT"));
                cv.setPomoTT(rs.getInt("pomoTT"));
                cv.setTrangThai(rs.getBoolean("TrangThai"));
                cv.setDoUuTien(rs.getInt("DoUuTien"));
                String thoiGianBatDauStr = rs.getString("ThoiGianBatDau");
                if(Objects.equals(thoiGianBatDauStr, null)){
                    cv.setThoiGianBatDau(null);
                }else {
                    LocalTime time =  LocalTime.parse(thoiGianBatDauStr, formatter1);
                    cv.setThoiGianBatDau(time);
                }
                cv.setMaBH(rs.getString("MaBH"));
                cvList.add(cv);
            }
        }catch(SQLException ex){
            System.out.println(ex);
        }finally{
            close();
        }
        return cvList;
    }

    public boolean hasCV(String congviec){
        boolean result = false;
        try{
            String sql = "SELECT * FROM CongViec WHERE MaCongViec = ?";

            conn = app.doan.DAL.DatabaseConnection.connect();
            p = conn.prepareStatement(sql);
            p.setString(1, congviec);
            ResultSet rs = p.executeQuery();
            result = rs.next();
        }catch(SQLException ex){
            System.out.println(ex);
        }finally{
            close();
        }
        return result;
    }

    public boolean themCV(DTO_CongViec cv){
        boolean result = false;
        try{
            String sql = "INSERT INTO CongViec VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            conn = app.doan.DAL.DatabaseConnection.connect();
            p = conn.prepareStatement(sql);
            p.setString(1, cv.getMaCV());
            p.setString(2, cv.getTenCV());
            if (cv.getThoiGian() != null) {
                p.setString(3, cv.getThoiGian().format(formatter));
            } else {
                p.setString(3, "Trống");
            }
            p.setString(4, cv.getGhiChu());
            p.setInt(5, cv.getPomoUT());
            p.setInt(6, cv.getPomoTT());
            p.setBoolean(7, cv.getTrangThai());
            p.setInt(8, cv.getDoUuTien());
            if (cv.getThoiGianBatDau() != null) {
                p.setString(9, cv.getThoiGianBatDau().format(formatter1));
            } else {
                p.setNull(9, Types.VARCHAR);
            }
            p.setString(10, cv.getMaBH());
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

    public boolean suaCV(DTO_CongViec cv){
        boolean result = false;
        try{
            String SQL = "UPDATE CongViec SET MaCongViec = ?, TenCongViec = ?, ThoiGian = ?, GhiChu = ?, pomoUT = ?, pomoTT = ?, TrangThai = ?, DoUuTien = ?, ThoiGianBatDau = ?, MaBH = ? WHERE MaCongViec = ? ";
            conn = app.doan.DAL.DatabaseConnection.connect();
            p = conn.prepareStatement(SQL);
            p.setString(1, cv.getMaCV());
            p.setString(2, cv.getTenCV());
            if (cv.getThoiGian() != null) {
                p.setString(3, cv.getThoiGian().format(formatter));
            } else {
                p.setString(3, "Trống");
            }
            p.setString(4, cv.getGhiChu());
            p.setInt(5, cv.getPomoUT());
            p.setInt(6, cv.getPomoTT());
            p.setBoolean(7, cv.getTrangThai());
            p.setInt(8, cv.getDoUuTien());
            if (cv.getThoiGianBatDau() != null) {
                p.setString(9, cv.getThoiGianBatDau().format(formatter1));
            } else {
                p.setNull(9, Types.VARCHAR);
            }
            p.setString(10, cv.getMaBH());
            p.setString(11, cv.getMaCV());
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

    public DTO_CongViec timtheomacv(String cv){
        try{
            String sql = "SELECT * FROM CongViec WHERE MaCongViec=?";
            conn = app.doan.DAL.DatabaseConnection.connect();
            p = conn.prepareStatement(sql);
            p.setString(1, cv);
            ResultSet rs = p.executeQuery();
            if(rs.next()){
                String ten = rs.getString("TenCongViec");
                LocalDate date = null;
                if("Trống".equals(rs.getString("ThoiGian"))){
                    date = null;
                }else {
                    date = LocalDate.parse(rs.getString("ThoiGian"), formatter);
                }
                String ghichu = rs.getString("GhiChu");
                int UT = rs.getInt("pomoUT");
                int TT = rs.getInt("pomoTT");
                boolean trangthai = rs.getBoolean("TrangThai");
                int dut = rs.getInt("DoUuTien");
                LocalTime time = null;
                if(Objects.equals(rs.getString("ThoiGianBatDau"), null)){
                    time = null;
                }else {
                    time =  LocalTime.parse(rs.getString("ThoiGianBatDau"), formatter1);
                }
                String maBH = rs.getString("MaBH");
                return new DTO_CongViec(cv, ten, ghichu, date, UT, TT, trangthai, dut, time, maBH);
            }
        }catch(SQLException ex){
            System.out.println(ex);
        }finally{
            close();
        }
        return null;
    }

    public ArrayList<DTO_CongViec> timtheoten(String ten){
        try{
            cvList.clear();
            String sql = "SELECT * FROM CongViec WHERE LOWER(TenCongViec) LIKE LOWER(?)";
            conn = app.doan.DAL.DatabaseConnection.connect();
            p = conn.prepareStatement(sql);
            p.setString(1, "%" + ten + "%");
            ResultSet rs = p.executeQuery();
            while(rs.next()){
                String macv = rs.getString("MaCongViec");
                String tencv = rs.getString("TenCongViec");
                LocalDate date = null;
                if("Trống".equals(rs.getString("ThoiGian"))){
                    date = null;
                }else {
                    date = LocalDate.parse(rs.getString("ThoiGian"), formatter);
                }
                String ghichu = rs.getString("GhiChu");
                int UT = rs.getInt("pomoUT");
                int TT = rs.getInt("pomoTT");
                boolean trangthai = rs.getBoolean("TrangThai");
                int dut = rs.getInt("DoUuTien");
                LocalTime time = null;
                if(Objects.equals(rs.getString("ThoiGianBatDau"), null)){
                    time = null;
                }else {
                    time =  LocalTime.parse(rs.getString("ThoiGianBatDau"), formatter1);
                }
                String maBH = rs.getString("MaBH");
                DTO_CongViec cv = new DTO_CongViec(macv, tencv, ghichu, date, UT, TT, trangthai, dut, time, maBH);
                cvList.add(cv);
            }
        }catch(SQLException ex){
            System.out.println(ex);
        }finally{
            close();
        }
        return cvList;
    }

    public boolean xoahp(DTO_CongViec cv){
        boolean result = false;
        try{
            String sql = "DELETE FROM CongViec WHERE MaCongViec = ?";
            p = conn.prepareStatement(sql);
            p.setString(1, cv.getMaCV());
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
