package inventory;

import java.util.Scanner;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Store{

	private static String getTimestamp(){
		ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String timestamp = now.format(formatter);
		return timestamp;	
	}

	void showProducts(){
		try{		
			String getQuery = "SELECT * FROM products";
			ResultSet rs = Connect.executeStatement(getQuery,1);
			System.out.println("\n --- PRODUCTS IN INVENTORY ---");
			System.out.format("%10s%24s%18s%10s\n","ProductId","Name","Price","Stock");
			while(rs.next()){
				int id = rs.getInt("productId");
				String productName = rs.getString("name");
				double price = rs.getDouble("price");
				int stock = rs.getInt("stock");
				System.out.format("%10d%24s%18f%10d\n",id,productName,price,stock);
			}
		}catch(SQLException e){
			System.out.println("Error occurred while showing products: "+e);
		} 
	}

	void purchase(int id, int quantity){ // only for customer
		try{
			String getQuery = "SELECT * FROM products WHERE productId="+id;
			ResultSet rs = Connect.executeStatement(getQuery,1);
			if(!rs.next()){
				System.out.println("TRANSACTION FAILED: Invalid product id");
				return;
			}
			int stock = rs.getInt("stock");
			String productName = rs.getString("name");
			double price = rs.getDouble("price");
			if(quantity > stock){
				System.out.println("TRANSACTION FAILED: Insufficient stock.");
				return;
			}
			stock -= quantity;
			String updateQuery = "UPDATE products SET stock="+stock+" WHERE productId="+id ;
			Connect.executeStatement(updateQuery,0);
			String recordTransaction = "INSERT INTO transactions(timestamp,productName,transactionType,price,quantity,amount,userType) VALUES('"+getTimestamp() + "','" + productName + "','purchase',"+price+","+quantity+","+ (price*quantity)+", 'customer')";
			System.out.println("TRANSACTION SUCCESSFUL! Amount = "+ (quantity * price));
			Connect.executeStatement(recordTransaction,0);
		}catch(Exception e){
			System.out.println("Error occurred while purchasing product: "+e);
		}
	}

	void add(String name, double price, int stock){ // only for seller, ek increase stock karke bhi rakhna
		try{
			String addQuery = "INSERT INTO products(name,price,stock) VALUES('"+name+"',"+price+","+stock+")";
			Connect.executeStatement(addQuery,0);
		}catch(Exception e){
			System.out.println("Error occurred while adding product: "+e);
		}
	}

	void updateStock(int id, int stock){
		if(stock < 0){
			System.out.println("UPDATE FAILED: Stock can not be negative.");
			return;
		}
		try{
			String getQuery = "SELECT * FROM products WHERE productId="+id;
			ResultSet rs = Connect.executeStatement(getQuery,1);
			if(!rs.next()){
				System.out.println("UPDATE FAILED: Invalid product id");
				return;
			}
			String updateQuery = "UPDATE products SET stock="+stock+" WHERE productId="+id ;
			Connect.executeStatement(updateQuery,0);
			System.out.println("UPDATE SUCCESSFUL!");
		}catch(Exception e){
			System.out.println("Error occurred while updating stock: "+e);
		}
	}

	void showTransactionHistory(){
		try{		
			String getQuery = "SELECT * FROM transactions";
			ResultSet rs = Connect.executeStatement(getQuery,1);
			System.out.println("\n --- TRANSACTION HISTORY ---");
			System.out.format("%14s%24s%20s%24s%18s%10s%18s%12s\n","TransactionId", "Timestamp", "TransactionType", "ProductName","Price","Quantity","Amount","UserType");
			while(rs.next()){
				int id = rs.getInt("transactionId");
				String transactionTime = rs.getString("timestamp");
				String transactionType = rs.getString("transactionType");
				String productName = rs.getString("productName");
				double price = rs.getDouble("price");
				int quantity = rs.getInt("quantity");
				double amount = rs.getDouble("amount");
				String userType = rs.getString("userType");
				System.out.format("%14s%24s%20s%24s%18s%10s%18s%12s\n", id, transactionTime, transactionType, productName, price, quantity, amount, userType);
			}
		}catch(SQLException e){
			System.out.println("Error occurred while showing transaction history: "+e);
		} 
	}

	public static void main(String args[]){
		Scanner sc = new Scanner(System.in);
		Store StoreManager = new Store();
		int choice, id, quantity, stock;
		double price;
		while(true){
			System.out.println("\nWelcome to the inventory!");
			System.out.println("Enter your choice: ");
			System.out.println("1. View a list of all products");
			System.out.println("2. Purchase a product");
			System.out.println("3. Add a new product");
			System.out.println("4. Update stock for existing product");
			System.out.println("5. View transaction history");
			System.out.print("Enter any other number to exit - ");
			choice = sc.nextInt();
			switch(choice){
				case 1:
					StoreManager.showProducts();
					break;
				case 2: 
					StoreManager.showProducts();
					System.out.print("Enter the id of the product you wish to buy: ");
					id = sc.nextInt();
					System.out.print("Enter the number of units you wish to buy: ");
					quantity = sc.nextInt();
					StoreManager.purchase(id,quantity);
					break;
				case 3:
					System.out.print("\nEnter product name: ");
					sc.nextLine();
					String name = sc.nextLine();
					System.out.print("Enter product price: ");
					price = sc.nextDouble();
					System.out.print("Enter product stock: ");
					stock = sc.nextInt();
					StoreManager.add(name,price,stock);
					break;
				case 4:
					StoreManager.showProducts();
					System.out.print("Enter the id of the product you wish to update: ");
					id = sc.nextInt();
					System.out.print("Enter updated stock: ");
					stock = sc.nextInt();
					StoreManager.updateStock(id,stock);
					break;
				case 5:
					StoreManager.showTransactionHistory();
					break;
				default:
					return;
			}
		}
	}
}
