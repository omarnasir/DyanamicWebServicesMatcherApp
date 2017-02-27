package wsComparator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import dataObjects.GenerateDataObjects;
import dataObjects.ServiceDetail;
import domParser.DomParser;
import postGres.PostgreSQLJDBC;

public class Main {

	public static void main(String[] args) {

		List<String> ServiceNames = new ArrayList<String>();

		try {
			PostgreSQLJDBC.connection();

			String sql = "SELECT \"WSDLName\" FROM \"Service\";";
			ResultSet result = PostgreSQLJDBC.executeSelectQuery(sql);

			while (result.next()) {
				ServiceNames.add(result.getString("WSDLName"));
			}

			if (ServiceNames.isEmpty()) {
				System.out.println("No Service Records exist in Database!");
				return;
			}
			
//			ServiceDetail serviceDetailOutputObj = GenerateDataObjects.populateWSDLServiceDetail(ServiceNames.get(23));
//			ServiceDetail serviceDetailInputObj = GenerateDataObjects.populateWSDLServiceDetail(ServiceNames.get(1));
//			WSMatcher.compareWSDLS(serviceDetailOutputObj, serviceDetailInputObj);
			
			for (int i = 0; i < ServiceNames.size(); i++) {//Iterate through all WSDLs
				ServiceDetail serviceDetailOutputObj = GenerateDataObjects.populateWSDLServiceDetail(ServiceNames.get(i)); //Populate output WSDL
				for (int j = 0; j < ServiceNames.size(); j++) {
					if(j == i)
					{
						continue;
					}
					ServiceDetail serviceDetailInputObj = GenerateDataObjects.populateWSDLServiceDetail(ServiceNames.get(j));
					WSMatcher.compareWSDLS(serviceDetailOutputObj, serviceDetailInputObj);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
	}

//	public static void main(String[] args) {
//		DomParser.domParser();
//	}
}
