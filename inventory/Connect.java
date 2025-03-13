package inventory;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DatabaseMetaData;

public class Connect{
	private static Connection conn;

	private static final String DB_URL = "jdbc:derby:memory:testDB;create=true";

	static{
		try{
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			conn = DriverManager.getConnection(DB_URL);
			Seed.seedDatabase();	
		}catch(Exception e){
			System.out.println("Error occurred while connecting to database: "+e);
		}
	}

	static ResultSet executeStatement(String query, int returnsSomething){
		ResultSet rs = null;
		try{
			Statement st = conn.createStatement();
			if(returnsSomething == 1)
				rs = st.executeQuery(query);
			else 
				st.executeUpdate(query);
			conn.commit();
		}catch(Exception e){
			System.out.println("Error occurred: "+e);
			System.out.println("While executing query: "+query);
		}
		return rs;
	}

	static void getDatabaseInfo(){
		try{
			DatabaseMetaData metaData = conn.getMetaData();
			ResultSet tables = metaData.getTables(null, "APP", null, new String[]{"TABLE"});

			System.out.println("Tables in the Derby database:");
			while (tables.next()) {
				String tableName = tables.getString("TABLE_NAME");
				System.out.println(tableName);
			}
		}catch(Exception e){
			System.out.println("Error while fetching meta data: "+e);
		}
	}

}
