package inventory;

class Seed{
	public static void main(String args[]){
		String createProductTable = "CREATE TABLE products(productId int, name varchar2(20), price double, stock int)";
		String createTransactionTable = "CREATE TABLE transaction(transactionID int, productName varchar2(20), price double, quantity int, buyerName varchar2(20), sellerName varchar2(20))";
		Connect.getInstance().executeStatement(createProductTable);
		Connect.getInstance().executeStatement(createTransactionTable);
	}
}
