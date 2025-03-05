package inventory;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Connect{
	private static Connection conn;
	private static Connect instance;

	private static String DB_URL = "jdbc:derby:memory:testDB;create=true";

	Connect(){
		try{
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			conn = DriverManager.getConnection(DB_URL);	
		}catch(Exception e){
			System.out.println("Error occurred while connecting to database: "+e);
		}
	}

	public static Connect getInstance(){
		if(instance==null) instance = new Connect();
		return instance;
	}	

	ResultSet executeStatement(String query, int num){
		try{
			Statement st = conn.createStatement();
			ResultSet rs;
			if(num==1)
				rs = st.executeQuery(query);
			else 
				rs = st.executeUpdate(query);
			return rs;
		}catch(Exception e){
			System.out.println("Error occurred: "+e);
			System.out.println("While executing query: "+query);
		}
		return null;
	}

	public static void main(String args[]){
		try{		
		String createProductTable = "CREATE TABLE products(productId int, name varchar(20), price double, stock int)";
		String createTransactionTable = "CREATE TABLE transaction(transactionID int, productName varchar(20), price double, quantity int, buyerName varchar(20), sellerName varchar(20))";
		Connect.getInstance().executeStatement(createProductTable,0);
		Connect.getInstance().executeStatement(createTransactionTable,0);
			String insertQuery = "INSERT INTO products VALUES(1,'Book',10,5)";	
			Connect.getInstance().executeStatement(insertQuery,0);
			String getQuery = "SELECT * FROM products";
			ResultSet rs = Connect.getInstance().executeStatement(getQuery,1);
			while(rs.next()){
				int id = rs.getInt("productId");
				System.out.println(id);
				String productName = rs.getString("name");
				System.out.println(productName);
				double price = rs.getDouble("price");
				System.out.println(price);
				int stock = rs.getInt("stock");
				System.out.println(stock);
			}
		}catch(SQLException e){
			System.out.println("Error occurred while executing query: "+e);
		}
	}
}
