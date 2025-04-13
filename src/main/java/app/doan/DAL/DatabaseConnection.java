package app.doan.DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    static String url = "jdbc:sqlite:C:\\Users\\pc\\IdeaProjects\\DoAn\\csdl.db";
    public static Connection c;

    public static Connection connect() {
        try {
        return DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println("Lỗi kết nối: " + e.getMessage());
            return null;
        }
    }

    public static void close(){
        try{
            if(c!=null){
                c.close();
            }
        }catch(SQLException ex){
            System.out.println(ex);
        }
    }
}
