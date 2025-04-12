package app.doan.DAL;

import app.doan.DTO.DTO_HocPhan;

import java.sql.*;
import java.util.ArrayList;

import static app.doan.DAL.DatabaseConnection.close;

public class DAL_HocPhan {
    public static Connection conn;
    public Statement stm = null;
    public static PreparedStatement p = null;
    public static ArrayList<DTO_HocPhan> hpList = new ArrayList<DTO_HocPhan>();

    public ArrayList<DTO_HocPhan> getallHPlist(){
        try{
            hpList.clear();
            String sql = "SELECT * FROM HocPhan";
            Connection conn = app.doan.DAL.DatabaseConnection.connect();
            assert conn != null;
            stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while(rs.next()){
                DTO_HocPhan hp = new DTO_HocPhan();
                hp.setMaHP(rs.getString("MaHocPhan"));
                hp.setTenHP(rs.getString("TenHocPhan"));
                hp.setGiangVien(rs.getString("GiangVien"));
                hp.setMoTa(rs.getString("MoTa"));
                hp.setTrangThai(rs.getBoolean("TrangThai"));
                hp.setMaTK(rs.getString("MaTaiKhoan"));
                hpList.add(hp);
            }
        }catch(SQLException ex){
            System.out.println(ex);
        }finally{
            close();
        }
        return hpList;
    }

    public boolean hasHP(String hocphan){
        boolean result = false;
        try{
            String sql = "SELECT * FROM HocPhan WHERE MaHocPhan = ?";

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

    public boolean themHP(DTO_HocPhan hp){
        boolean result = false;
        try{
            String sql = "INSERT INTO HocPhan VALUES(?, ?, ?, ?, ?, ?)";
            conn = app.doan.DAL.DatabaseConnection.connect();
            p = conn.prepareStatement(sql);
            p.setString(1, hp.getMaHP());
            p.setString(2, hp.getTenHP());
            p.setString(3, hp.getGiangVien());
            p.setString(4, hp.getMoTa());
            p.setBoolean(5, hp.getTrangThai());
            p.setString(6, hp.getMaTK());
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

    public boolean suaHP(DTO_HocPhan hp){
        boolean result = false;
        try{
            String SQL = "UPDATE HocPhan SET MaHocPhan = ?, TenHocPhan = ?, GiangVien = ?, MoTa = ?, TrangThai = ?, MaTaiKhoan = ? WHERE MaHocPhan = ? ";
            conn = app.doan.DAL.DatabaseConnection.connect();
            p = conn.prepareStatement(SQL);
            p.setString(1, hp.getMaHP());
            System.out.println(hp.getMaHP());
            p.setString(2, hp.getTenHP());
            p.setString(3, hp.getGiangVien());
            p.setString(4, hp.getMoTa());
            p.setBoolean(5, hp.getTrangThai());
            p.setString(6, hp.getMaTK());
            p.setString(7, hp.getMaHP());
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

    public DTO_HocPhan timtheomahp(String hp){
        try{
                String sql = "SELECT * FROM HocPhan WHERE MaHocPhan=?";
                conn = app.doan.DAL.DatabaseConnection.connect();
                p = conn.prepareStatement(sql);
                p.setString(1, hp);
                ResultSet rs = p.executeQuery();
                if(rs.next()){
                    String tenhp = rs.getString("TenHocPhan");
                    String giangvien = rs.getString("GiangVien");
                    boolean laplai = rs.getBoolean("LapLai");
                    String mota = rs.getString("MoTa");
                    boolean trangthai = rs.getBoolean("TrangThai");
                    String maTK = rs.getString("MaTaiKhoan");
                    DTO_HocPhan h = new DTO_HocPhan(hp, tenhp, giangvien, laplai, mota, trangthai, maTK);
                    return h;
                }
        }catch(SQLException ex){
            System.out.println(ex);
        }finally{
            close();
        }
        return null;
    }

    public ArrayList<DTO_HocPhan> timtheoten(String tenhp){
        try{
                hpList.clear();
                String sql = "SELECT * FROM HocPhan WHERE LOWER(TenHocPhan) LIKE LOWER(?)";
                conn = app.doan.DAL.DatabaseConnection.connect();
                p = conn.prepareStatement(sql);
                p.setString(1, "%" + tenhp + "%");
                ResultSet rs = p.executeQuery();
                while(rs.next()){
                    String mahp = rs.getString("MaHocPhan");
                    String ten = rs.getString("TenHocPhan");
                    String giangvien = rs.getString("GiangVien");
                    boolean laplai = rs.getBoolean("LapLai");
                    String mota = rs.getString("MoTa");
                    boolean trangthai = rs.getBoolean("TrangThai");
                    String matk = rs.getString("MaTK");
                    DTO_HocPhan hp = new DTO_HocPhan(mahp, ten, giangvien, laplai, mota, trangthai, matk);
                    hpList.add(hp);
                }
        }catch(SQLException ex){
            System.out.println(ex);
        }finally{
            close();
        }
        return hpList;
    }

    public boolean xoahp(DTO_HocPhan hp){
        boolean result = false;
            try{
                String sql = "DELETE FROM HocPhan WHERE MaHocPhan = ?";
                p = conn.prepareStatement(sql);
                p.setString(1, hp.getMaHP());
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
