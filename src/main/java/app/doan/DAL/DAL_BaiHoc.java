//package app.doan.DAL;
//
//import app.doan.DTO.DTO_BaiHoc;
//
//import java.sql.*;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//
//import static app.doan.DAL.DatabaseConnection.close;
//
//public class DAL_BaiHoc {
//    public static Connection conn;
//    public Statement stm = null;
//    public static PreparedStatement p = null;
//    public static ArrayList<DTO_BaiHoc> cList = new ArrayList<DTO_BaiHoc>();
//
//    public ArrayList<DTO_BaiHoc> getallBHist(){
//        try{
//            cList.clear();
//            String sql = "SELECT * FROM BaiHoc";
//            Connection conn = app.doan.DAL.DatabaseConnection.connect();
//            assert conn != null;
//            stm = conn.createStatement();
//            ResultSet rs = stm.executeQuery(sql);
//            while(rs.next()){
//                DTO_BaiHoc c = new DTO_BaiHoc();
//                c.setMaBH(rs.getString("MaBaiHoc"));
//                c.setTenBH(rs.getString("TenBaiHoc"));
//                c.setGhiChu(rs.getString("GhiChu"));
//                Timestamp sqlTimestamp = rs.getTimestamp("NgayHoc");
//                LocalDateTime localdt = sqlTimestamp.toLocalDateTime();
//                c.setGhiChu(rs.getString("NgayHoc"));
//                c.setTrangThai(rs.getBoolean("TrangThai"));
//                c.setMaHP(rs.getString("MaChuong"));
//                cList.add(c);
//            }
//        }catch(SQLException ex){
//            System.out.println(ex);
//        }finally{
//            close();
//        }
//        return cList;
//    }
//
//    public boolean hasC(String hocphan){
//        boolean result = false;
//        try{
//            String sql = "SELECT * FROM BaiHoc WHERE MaBaiHoc = ?";
//
//            conn = app.doan.DAL.DatabaseConnection.connect();
//            p = conn.prepareStatement(sql);
//            p.setString(1, hocphan);
//            ResultSet rs = p.executeQuery();
//            result = rs.next();
//        }catch(SQLException ex){
//            System.out.println(ex);
//        }finally{
//            close();
//        }
//        return result;
//    }
//
//    public boolean themC(DTO_BaiHoc c){
//        boolean result = false;
//        try{
//            String sql = "INSERT INTO BaiHoc VALUES(?, ?, ?, ?, ?)";
//            conn = app.doan.DAL.DatabaseConnection.connect();
//            p = conn.prepareStatement(sql);
//            p.setString(1, c.getMaChuong());
//            p.setString(2, c.getTenBaiHoc());
//            p.setString(3, c.getMoTa());
//            p.setBoolean(4, c.getTrangThai());
//            p.setString(5, c.getMaHP());
//            if(p.executeUpdate() >= 1){
//                result = true;
//            }
//        }catch(SQLException ex){
//            System.out.println(ex);
//        }finally{
//            close();
//        }
//        return result;
//    }
//
//    public boolean suaC(DTO_BaiHoc c){
//        boolean result = false;
//        try{
//            String SQL = "UPDATE HocPhan SET MaHocPhan = ?, TenHocPhan = ?, GiangVien = ?, LapLai = ?, MoTa = ?, TrangThai = ?, MaHocPhan = ? WHERE Email = ? ";
//            conn = app.doan.DAL.DatabaseConnection.connect();
//            p = conn.prepareStatement(SQL);
//            p.setString(1, c.getMaChuong());
//            p.setString(2, c.getTenBaiHoc());
//            p.setString(3, c.getMoTa());
//            p.setBoolean(4, c.getTrangThai());
//            p.setString(5, c.getMaHP());
//            p.setString(6, c.getMaChuong());
//            p.executeUpdate();
//            if(p.executeUpdate() >= 1){
//                result = true;
//            }
//        }catch(SQLException ex){
//            System.out.println(ex);
//        }finally{
//            close();
//        }
//        return result;
//    }
//
//    public DTO_BaiHoc timtheomac(String mac){
//        try{
//            String sql = "SELECT * FROM BaiHoc WHERE MaBaiHoc=?";
//            conn = app.doan.DAL.DatabaseConnection.connect();
//            p = conn.prepareStatement(sql);
//            p.setString(1, mac);
//            ResultSet rs = p.executeQuery();
//            if(rs.next()){
//                String tenc = rs.getString("TenBaiHoc");
//                String mota = rs.getString("MoTa");
//                boolean trangthai = rs.getBoolean("TrangThai");
//                String mahp = rs.getString("MaBaiHoc");
//                DTO_BaiHoc c = new DTO_BaiHoc(mac, tenc, mota, trangthai, mahp);
//                return c;
//            }
//        }catch(SQLException ex){
//            System.out.println(ex);
//        }finally{
//            close();
//        }
//        return null;
//    }
//
//    public ArrayList<DTO_BaiHoc> timtheoten(String tenc){
//        try{
//            cList.clear();
//            String sql = "SELECT * FROM BaiHoc WHERE LOWER(TenBaiHoc) LIKE LOWER(?)";
//            conn = app.doan.DAL.DatabaseConnection.connect();
//            p = conn.prepareStatement(sql);
//            p.setString(1, "%" + tenc + "%");
//            ResultSet rs = p.executeQuery();
//            while(rs.next()){
//                String mac = rs.getString("MaBaiHoc");
//                String ten = rs.getString("TenBaiHoc");
//                String mota = rs.getString("MoTa");
//                boolean trangthai = rs.getBoolean("TrangThai");
//                String mahp = rs.getString("MaHocPhan");
//                DTO_BaiHoc kh = new DTO_BaiHoc(mac, ten, mota, trangthai, mahp);
//                cList.add(kh);
//            }
//        }catch(SQLException ex){
//            System.out.println(ex);
//        }finally{
//            close();
//        }
//        return cList;
//    }
//
//    public boolean xoaC(DTO_BaiHoc kh){
//        boolean result = false;
//        try{
//            String sql = "DELETE FROM BaiHoc WHERE MaBaiHoc = ?";
//            p = conn.prepareStatement(sql);
//            p.setString(1, kh.getMaHP());
//            if(p.executeUpdate() >= 1){
//                result = true;
//            }
//        }catch(SQLException ex){
//            System.out.println(ex);
//        }finally{
//            close();
//        }
//        return result;
//    }
//}
