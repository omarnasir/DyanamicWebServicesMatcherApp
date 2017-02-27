package postGres;

public class SQLScripts {
	public static void serviceQuery(String serviceName, String wsdlName)
	{
		String sql = "INSERT INTO \"Service\" (\"ServiceID\",\"ServiceName\", \"WSDLName\") "
				 + "VALUES (nextval('\"seqService\"'), '" + serviceName + "' , '" + wsdlName + "');";
		PostgreSQLJDBC.executeInsertQuery(sql);
	}
	
	public static void operationQuery(String operationName, String messageName, Integer operationType)
	{
		 String sql = "INSERT INTO \"Operation\" (\"OperationID\",\"ServiceID\",\"OperationName\",\"MessageName\",\"OperationType\") " 
		 + "VALUES (nextval('\"seqOperation\"'),currval('\"seqService\"'), '"
		 + operationName + "', '" + messageName + "','" + operationType + "' );";
		 PostgreSQLJDBC.executeInsertQuery(sql);
	}
	
	public static void elementQuery(String messageName, String elementName, String wsdlName)
	{
		 String sql = "INSERT INTO \"Element\" (\"ElementID\",\"OperationID\",\"ElementName\")"
		 + "VALUES (nextval('\"seqElement\"'),(SELECT O.\"OperationID\" FROM \"Operation\" AS O, \"Service\" AS S "
		 + "WHERE O.\"MessageName\" = '" + messageName + "' AND S.\"WSDLName\" = '" + wsdlName + "' "
		 		+ "AND S.\"ServiceID\" = O.\"ServiceID\"), '" + elementName + "');";
		 PostgreSQLJDBC.executeInsertQuery(sql);
	}
	
	public static void annotationQuery(String messageName, String elementName, String annotation, String wsdlName)
	{
		 String sql = "INSERT INTO \"Element\" (\"ElementID\",\"OperationID\",\"ElementName\", \"Annotation\")"
		 + "VALUES (nextval('\"seqElement\"'),(SELECT O.\"OperationID\" FROM \"Operation\" AS O, \"Service\" AS S "
		 + "WHERE O.\"MessageName\" = '" + messageName + "' AND S.\"WSDLName\" = '" + wsdlName + "' "
		 		+ "AND S.\"ServiceID\" = O.\"ServiceID\"), '" + elementName + "' , '" + annotation + "');";
		 PostgreSQLJDBC.executeInsertQuery(sql);
	}
	
	public static void deleteDatabase()
	{
		 String sql = "DELETE FROM \"Service\";";
		 PostgreSQLJDBC.executeDeleteQuery(sql);
		 sql = "DELETE FROM \"Operation\";";
		 PostgreSQLJDBC.executeDeleteQuery(sql);
		 sql = "DELETE FROM \"Element\";";
		 PostgreSQLJDBC.executeDeleteQuery(sql);
		 sql = "ALTER SEQUENCE \"seqService\" RESTART;";
		 PostgreSQLJDBC.executeDeleteQuery(sql);
		 sql = "ALTER SEQUENCE \"seqOperation\" RESTART;";
		 PostgreSQLJDBC.executeDeleteQuery(sql);
		 sql = "ALTER SEQUENCE \"seqElement\" RESTART;";
		 PostgreSQLJDBC.executeDeleteQuery(sql);
	}
}
