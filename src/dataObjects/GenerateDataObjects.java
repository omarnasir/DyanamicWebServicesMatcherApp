package dataObjects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import postGres.PostgreSQLJDBC;

public class GenerateDataObjects {
	
	public static ServiceDetail populateWSDLServiceDetail(String ServiceName)
			throws SQLException {
		String sql;
		ResultSet result;
		sql = "SELECT O.\"OperationName\",O.\"OperationType\",E.\"ElementName\" from \"Element\" AS E,\"Operation\" "
				+ "AS O, \"Service\" As S " + "Where S.\"ServiceID\" = O.\"ServiceID\" "
				+ "And O.\"OperationID\" = E.\"OperationID\" " + "AND O.\"OperationType\" = '0' "
				+ "AND S.\"WSDLName\" = '" + ServiceName + "';";
//						+ "AND S.\"ServiceName\" = 'AccountService';";
				
		result = PostgreSQLJDBC.executeSelectQuery(sql);
		
		String OperationNameTemp = "";
		
		ServiceDetail serviceDetailOBj = new ServiceDetail(); //Create new ServiceDetail Obj
		serviceDetailOBj.setServiceName(ServiceName); //Set its name
		
		List<ServiceDetail.Operation> operationList = serviceDetailOBj.getOperations(); //Create Operations list for Service obj
		List<ServiceDetail.Element> elementListObj = null;
		while(result.next())
		{
			String OperationName = result.getString("OperationName");
			if(OperationName.compareTo(OperationNameTemp) != 0) //Found new operation
			{
				OperationNameTemp = OperationName;
				
				ServiceDetail.Operation operationObj = new ServiceDetail.Operation();
				operationObj.setOperationName(OperationName);
				operationList.add(operationObj);
				
				elementListObj = operationObj.getElementName();
			}
			else
			{
				elementListObj = operationList.get(operationList.size() -1).getElementName();
			}
			ServiceDetail.Element elementObj = new ServiceDetail.Element();
			elementObj.setElementName(result.getString("ElementName"));
			elementListObj.add(elementObj);
		}
		return serviceDetailOBj;
	}

}
