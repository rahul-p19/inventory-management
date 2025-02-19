package inventory;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;

class Connect{
	static Connection conn;
	private String DB_URL = "";
	private String DB_USER = "";
	private String DB_PASSWORD = "";
	Connect(){
		try{
			conn = new DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);	
		}catch(Exception e){
			System.out.println("Error occurred while connecting to database: "+e);
		}
	}
	void executeStatement(String query){
		try{
			Statement st = conn.createStatement();
			st.executeUpdate(query);
		}catch(Exception e){
			System.out.println("Error occurred: "+e);
			System.out.println("While executing query: "+query);
		}
	}
}
