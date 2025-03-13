package inventory;

class Seed{
	public static void seedDatabase(){
		String createProductTable = "CREATE TABLE products(productId int NOT NULL GENERATED ALWAYS AS IDENTITY, name varchar(20), price double, stock int)";
		String createPurchasesTable = "CREATE TABLE purchases(purchaseId int, productName varchar(20), price double, quantity int, buyerName varchar(20))";
		Connect.executeStatement(createProductTable,0);
		Connect.executeStatement(createPurchasesTable,0);
	}
}
