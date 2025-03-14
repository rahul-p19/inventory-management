package inventory;

class Seed{
	public static void main(String args[]){
		String createProductTable = "CREATE TABLE products(productId int NOT NULL GENERATED ALWAYS AS IDENTITY, name varchar(20), price double, stock int)";
		String createTransactionsTable = "CREATE TABLE transactions(transactionId int NOT NULL GENERATED ALWAYS AS IDENTITY, timestamp varchar(20), productName varchar(20), transactionType varchar(15), price double, quantity int, amount double, userType varchar(20))";
		Connect.executeStatement(createProductTable,0);
		Connect.executeStatement(createTransactionsTable,0);
	}
}
