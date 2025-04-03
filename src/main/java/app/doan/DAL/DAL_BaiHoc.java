package app.doan.DAL;

import app.doan.DTO.DTO_BaiHoc;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static app.doan.DAL.DatabaseConnection.close;

public class DAL_BaiHoc {
    public static Connection conn;
    public Statement stm = null;
    public static PreparedStatement p = null;
    public static ArrayList<DTO_BaiHoc> bhList = new ArrayList<DTO_BaiHoc>();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public ArrayList<DTO_BaiHoc> getallBHlist(){
        try{
            bhList.clear();
            String sql = "SELECT * FROM BaiHoc";
            Connection conn = app.doan.DAL.DatabaseConnection.connect();
            assert conn != null;
            stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while(rs.next()){
                DTO_BaiHoc c = new DTO_BaiHoc();
                c.setMaBH(rs.getString("MaBaiHoc"));
                c.setTenBH(rs.getString("TenBaiHoc"));
                c.setGhiChu(rs.getString("GhiChu"));
                if("trong".equals(rs.getString("NgayHoc"))){
                    c.setNgayHoc(null);
                }else {
                    LocalDateTime dateTime = LocalDateTime.parse(rs.getString("NgayHoc"), formatter);
                    c.setNgayHoc(dateTime);
                }
                c.setTrangThai(rs.getBoolean("TrangThai"));
                c.setMaChuong(rs.getString("MaChuong"));
                bhList.add(c);
            }
        }catch(SQLException ex){
            System.out.println(ex);
        }finally{
            close();
        }
        return bhList;
    }

    public boolean hasBH(String hocphan){
        boolean result = false;
        try{
            String sql = "SELECT * FROM BaiHoc WHERE MaBaiHoc = ?";

            conn = app.doan.DAL.DatabaseConnection.connect();
            p = conn.prepareStatement(sql);
            p.setString(1, hocphan);
            ResultSet rs = p.executeQuery();
            result = rs.next();
        }catch(SQLException ex){
            System.out.println(ex);
        }finally{
            close();
        }
        return result;
    }

    public boolean themC(DTO_BaiHoc c){
        boolean result = false;
        try{
            String sql = "INSERT INTO BaiHoc VALUES(?, ?, ?, ?, ?, ?)";
            conn = app.doan.DAL.DatabaseConnection.connect();
            p = conn.prepareStatement(sql);
            p.setString(1, c.getMaBH());
            p.setString(2, c.getTenBH());
            p.setString(3, c.getGhiChu());
            if (c.getNgayHoc() != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                p.setString(3, c.getNgayHoc().format(formatter));
            } else {
                p.setString(3, "trong");
            }
            p.setString(4, c.getNgayHoc().format(formatter));
            p.setBoolean(5, c.getTrangThai());
            p.setString(6, c.getMaChuong());
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

    public boolean suaC(DTO_BaiHoc c){
        boolean result = false;
        try{
            String SQL = "UPDATE BaiHoc SET MaBaiHoc = ?, TenBaiHoc = ?, GhiChu = ?, NgayHoc = ?, TrangThai = ?, MaChuong = ? WHERE MaBaiHoc = ? ";
            conn = app.doan.DAL.DatabaseConnection.connect();
            p = conn.prepareStatement(SQL);
            p.setString(1, c.getMaBH());
            p.setString(2, c.getTenBH());
            p.setString(3, c.getGhiChu());
            p.setString(4, c.getNgayHoc().format(formatter));
            p.setBoolean(5, c.getTrangThai());
            p.setString(6, c.getMaChuong());
            p.setString(7, c.getMaBH());
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

    public DTO_BaiHoc timtheomac(String ma){
        try{
            String sql = "SELECT * FROM BaiHoc WHERE MaBaiHoc=?";
            conn = app.doan.DAL.DatabaseConnection.connect();
            p = conn.prepareStatement(sql);
            p.setString(1, ma);
            ResultSet rs = p.executeQuery();
            if(rs.next()){
                String ten = rs.getString("TenBaiHoc");
                String mota = rs.getString("GhiChu");
                LocalDateTime dt = LocalDateTime.parse(rs.getString("NgayHoc"), formatter);
                boolean trangthai = rs.getBoolean("TrangThai");
                String mahp = rs.getString("MaChuong");
                DTO_BaiHoc c = new DTO_BaiHoc(ma, ten, mota, dt, trangthai, mahp);
                return c;
            }
        }catch(SQLException ex){
            System.out.println(ex);
        }finally{
            close();
        }
        return null;
    }

    public ArrayList<DTO_BaiHoc> timtheoten(String tenc){
        try{
            bhList.clear();
            String sql = "SELECT * FROM BaiHoc WHERE LOWER(TenBaiHoc) LIKE LOWER(?)";
            conn = app.doan.DAL.DatabaseConnection.connect();
            p = conn.prepareStatement(sql);
            p.setString(1, "%" + tenc + "%");
            ResultSet rs = p.executeQuery();
            while(rs.next()){
                String ma = rs.getString("MaBaiHoc");
                String ten = rs.getString("TenBaiHoc");
                String mota = rs.getString("GhiChu");
                LocalDateTime dt = LocalDateTime.parse(rs.getString("NgayHoc"), formatter);
                boolean trangthai = rs.getBoolean("TrangThai");
                String mahp = rs.getString("MaChuong");
                DTO_BaiHoc kh = new DTO_BaiHoc(ma, ten, mota, dt, trangthai, mahp);
                bhList.add(kh);
            }
        }catch(SQLException ex){
            System.out.println(ex);
        }finally{
            close();
        }
        return bhList;
    }

    public boolean xoaC(DTO_BaiHoc kh){
        boolean result = false;
        try{
            String sql = "DELETE FROM BaiHoc WHERE MaBaiHoc = ?";
            p = conn.prepareStatement(sql);
            p.setString(1, kh.getMaBH());
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
