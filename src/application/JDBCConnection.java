package application;
import java.io.File;
import java.io.IOException;
import java.sql.*;
public class JDBCConnection {
	 static Connection c = null;
	public static Connection connector() {
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:./DB/FavSong.db");
			 return c;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	     
	}
	
	public static void createFolder() {
		File folder=new File("DB");
		folder.mkdir();
	}
}
