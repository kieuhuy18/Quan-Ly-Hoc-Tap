package app.doan.DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    //private static final String DB_PATH = Paths.get("src/main/resources/database.db").toAbsolutePath().toString();
    //private static final String URL = "jdbc:sqlite:" + DB_PATH;
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
