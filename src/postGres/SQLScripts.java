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
}
