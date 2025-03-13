all: inventory/Store.java
	echo "Starting up your inventory..."
	javac -cp ".:db/derby.jar:db/derbyclient.jar:db/derbynet.jar:db/derbytools.jar:inventory" inventory/Connect.java
	javac -cp ".:db/derby.jar:db/derbyclient.jar:db/derbynet.jar:db/derbytools.jar:inventory" inventory/Seed.java
	javac -cp ".:db/derby.jar:db/derbyclient.jar:db/derbynet.jar:db/derbytools.jar:inventory" inventory/Store.java
	java -cp ".:db/derby.jar:db/derbyclient.jar:db/derbynet.jar:db/derbytools.jar:inventory" inventory/Store

.SILENT:
