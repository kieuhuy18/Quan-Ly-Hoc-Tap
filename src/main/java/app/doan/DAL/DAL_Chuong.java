package app.doan.DAL;

import app.doan.DTO.DTO_Chuong;

import java.sql.*;
import java.util.ArrayList;

import static app.doan.DAL.DatabaseConnection.close;

public class DAL_Chuong {
    public static Connection conn;
    public Statement stm = null;
    public static PreparedStatement p = null;
    public static ArrayList<DTO_Chuong> cList = new ArrayList<DTO_Chuong>();

    public ArrayList<DTO_Chuong> getallClist(){
        try{
            cList.clear();
            String sql = "SELECT * FROM Chuong";
            Connection conn = app.doan.DAL.DatabaseConnection.connect();
            assert conn != null;
            stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while(rs.next()){
                DTO_Chuong c = new DTO_Chuong();
                c.setMaChuong(rs.getString("MaChuong"));
                c.setTenChuong(rs.getString("TenChuong"));
                c.setMoTa(rs.getString("MoTa"));
                c.setTrangThai(rs.getBoolean("TrangThai"));
                c.setMaHP(rs.getString("MaHocPhan"));
                cList.add(c);
            }
        }catch(SQLException ex){
            System.out.println(ex);
        }finally{
            close();
        }
        return cList;
    }

    public boolean hasC(String hocphan){
        boolean result = false;
        try{
            String sql = "SELECT * FROM Chuong WHERE MaChuong = ?";

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

    public boolean themC(DTO_Chuong c){
        boolean result = false;
        try{
            String sql = "INSERT INTO Chuong VALUES(?, ?, ?, ?, ?)";
            conn = app.doan.DAL.DatabaseConnection.connect();
            p = conn.prepareStatement(sql);
            p.setString(1, c.getMaChuong());
            p.setString(2, c.getTenChuong());
            p.setString(3, c.getMoTa());
            p.setBoolean(4, c.getTrangThai());
            p.setString(5, c.getMaHP());
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

    public boolean suaC(DTO_Chuong c){
        boolean result = false;
        try{
            String SQL = "UPDATE HocPhan SET MaChuong= ?, TenChuong = ?, MoTa = ?, TrangThai = ?, MaHocPhan = ? WHERE MaChuong = ? ";
            conn = app.doan.DAL.DatabaseConnection.connect();
            p = conn.prepareStatement(SQL);
            p.setString(1, c.getMaChuong());
            p.setString(2, c.getTenChuong());
            p.setString(3, c.getMoTa());
            p.setBoolean(4, c.getTrangThai());
            p.setString(5, c.getMaHP());
            p.setString(6, c.getMaChuong());
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

    public DTO_Chuong timtheomac(String mac){
        try{
            String sql = "SELECT * FROM Chuong WHERE MaChuong=?";
            conn = app.doan.DAL.DatabaseConnection.connect();
            p = conn.prepareStatement(sql);
            p.setString(1, mac);
            ResultSet rs = p.executeQuery();
            if(rs.next()){
                String tenc = rs.getString("TenChuong");
                String mota = rs.getString("MoTa");
                boolean trangthai = rs.getBoolean("TrangThai");
                String mahp = rs.getString("MaChuong");
                DTO_Chuong c = new DTO_Chuong(mac, tenc, mota, trangthai, mahp);
                return c;
            }
        }catch(SQLException ex){
            System.out.println(ex);
        }finally{
            close();
        }
        return null;
    }

    public ArrayList<DTO_Chuong> timtheoten(String tenc){
        try{
            cList.clear();
            String sql = "SELECT * FROM Chuong WHERE LOWER(TenChuong) LIKE LOWER(?)";
            conn = app.doan.DAL.DatabaseConnection.connect();
            p = conn.prepareStatement(sql);
            p.setString(1, "%" + tenc + "%");
            ResultSet rs = p.executeQuery();
            while(rs.next()){
                String mac = rs.getString("MaChuong");
                String ten = rs.getString("TenChuong");
                String mota = rs.getString("MoTa");
                boolean trangthai = rs.getBoolean("TrangThai");
                String mahp = rs.getString("MaHocPhan");
                DTO_Chuong kh = new DTO_Chuong(mac, ten, mota, trangthai, mahp);
                cList.add(kh);
            }
        }catch(SQLException ex){
            System.out.println(ex);
        }finally{
            close();
        }
        return cList;
    }

    public boolean xoaC(DTO_Chuong kh){
        boolean result = false;
        try{
            String sql = "DELETE FROM Chuong WHERE MaChuong = ?";
            p = conn.prepareStatement(sql);
            p.setString(1, kh.getMaHP());
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
