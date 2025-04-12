package app.doan.DAL;

import app.doan.DTO.DTO_TaiKhoan;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

import static app.doan.DAL.DatabaseConnection.close;
import static app.doan.GUI.HTDangNhap.MaND;

public class DAL_TaiKhoan {
    public static Connection conn;
    public Statement stm = null;
    public PreparedStatement p = null;
    public static ArrayList<DTO_TaiKhoan> tkList = new ArrayList<DTO_TaiKhoan>();

    public ArrayList<DTO_TaiKhoan> getallTKlist(){
            try{
                tkList.clear();
                String sql = "SELECT * FROM TaiKhoan";
                Connection conn = app.doan.DAL.DatabaseConnection.connect();
                assert conn != null;
                stm = conn.createStatement();
                ResultSet rs = stm.executeQuery(sql);
                while(rs.next()){
                    DTO_TaiKhoan tk = new DTO_TaiKhoan();
                    tk.setEmail(rs.getString("Email"));
                    tk.setTenND(rs.getString("Ten"));
                    tk.setMatKhau(rs.getString("MatKhau"));
                    tkList.add(tk);
                }
            }catch(SQLException ex){
                System.out.println(ex);
            }finally{
                close();
            }
        return tkList;
    }

    public boolean hasTK(String taiKhoan){
        boolean result = false;
        try{
            String sql = "SELECT * FROM TaiKhoan WHERE Email = ?";

            conn = app.doan.DAL.DatabaseConnection.connect();
            p = conn.prepareStatement(sql);
            p.setString(1, taiKhoan);
            ResultSet rs = p.executeQuery();
            result = rs.next();
        }catch(SQLException ex){
            System.out.println(ex);
        }finally{
            close();
        }
        return result;
    }

    public boolean DangKy(DTO_TaiKhoan tk){
        boolean result = false;
            try{
                String sql = "INSERT INTO TaiKhoan VALUES(?, ?, ?)";
                conn = app.doan.DAL.DatabaseConnection.connect();
                p = conn.prepareStatement(sql);
                p.setString(1, tk.getEmail());
                p.setString(2, tk.getTenND());
                p.setString(3, tk.getMatKhau());
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

    public boolean suaTK(DTO_TaiKhoan tk){
        boolean result = false;
            try{
                String SQL = "UPDATE TaiKhoan SET Email = ?, Ten = ?, MatKhau = ? WHERE Email = ? ";
                conn = app.doan.DAL.DatabaseConnection.connect();
                p = conn.prepareStatement(SQL);
                p.setString(1, tk.getEmail());
                p.setString(2, tk.getTenND());
                p.setString(3, tk.getMatKhau());
                p.setString(4, tk.getEmail());
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

    public boolean sua(DTO_TaiKhoan tk){
        boolean result = false;
        try{
            String SQL = "UPDATE TaiKhoan SET Email = ?, Ten = ?, MatKhau = ? WHERE Email = ? ";
            conn = app.doan.DAL.DatabaseConnection.connect();
            p = conn.prepareStatement(SQL);
            p.setString(1, MaND);
            p.setString(2, tk.getTenND());
            p.setString(3, tk.getMatKhau());
            p.setString(4, tk.getEmail());
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

    public DTO_TaiKhoan LayTK(String email){
        DTO_TaiKhoan mk = new DTO_TaiKhoan();
        try{
            String sql = "SELECT * FROM TaiKhoan WHERE Email = ? ";
            conn = app.doan.DAL.DatabaseConnection.connect();
            p = conn.prepareStatement(sql);
            p.setString(1, email);
            ResultSet rs = p.executeQuery();
            while(rs.next()){
                mk.setEmail(email);
                mk.setTenND(rs.getString("Ten"));
                mk.setMatKhau(rs.getString("MatKhau"));
                return mk;
            }
        }catch(SQLException ex){
            System.out.println(ex);
        }finally{
            close();
        }
        return mk;
    }

    public void QuenMatKhau(DTO_TaiKhoan tk){
        final String mail = "kieuhuy180903@gmail.com";
        final String pass = "znqz lptg bvus tgyt";
        // Cấu hình SMTP
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Tạo session
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mail,pass);
            }
        });

        try {
            // Tạo message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mail, "He thong"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(tk.getEmail()));
            message.setSubject("Khoi phuc mat khau");
            message.setText("Mat khau tai khoan cua ban: " + tk.getMatKhau());
            System.out.println("Thanh cong");

            // Gửi email
            Transport.send(message);
            System.out.println("Email đã gửi thành công!");
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
