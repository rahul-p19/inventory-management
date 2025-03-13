package inventory;

import java.util.Scanner;
import java.sql.SQLException;
import java.sql.ResultSet;

public class Store{
	void showProducts(){
		try{		
			String getQuery = "SELECT * FROM products";
			ResultSet rs = Connect.executeStatement(getQuery,1);
			System.out.println("\n --- PRODUCTS IN INVENTORY ---");
			System.out.format("%2s%32s%18s%10s\n","Id","Name","Price","Stock");
			while(rs.next()){
				int id = rs.getInt("productId");
				String productName = rs.getString("name");
				double price = rs.getDouble("price");
				int stock = rs.getInt("stock");
				System.out.format("%2d%32s%18f%10d\n",id,productName,price,stock);
			}
		}catch(SQLException e){
			System.out.println("Error occurred while showing products: "+e);
		} 
	}
	void purchase(){ // only for customer
	
	}
	void add(String name, double price, int stock){ // only for seller, ek increase stock karke bhi rakhna
		try{
			String addQuery = "INSERT INTO products(name,price,stock) VALUES('"+name+"',"+price+","+stock+")";
			Connect.executeStatement(addQuery,0);
		}catch(Exception e){
			System.out.println("Error occurred while adding product: "+e);
		}
	}
	void updateStock(){

	}
	void showTransactionHistory(){
		Connect.getDatabaseInfo();
	}

	public static void main(String args[]){
		Scanner sc = new Scanner(System.in);
		Store StoreManager = new Store();
		int choice;
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
					StoreManager.purchase();
					break;
				case 3:
					System.out.print("\nEnter product name: ");
					sc.nextLine();
					String name = sc.nextLine();
					System.out.print("Enter product price: ");
					double price = sc.nextDouble();
					System.out.print("Enter product stock: ");
					int stock = sc.nextInt();
					StoreManager.add(name,price,stock);
					break;
				case 4:
					StoreManager.updateStock();
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
