package wsComparator;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import dataObjects.GenerateDataObjects;
import dataObjects.ServiceDetail;
import domParser.DomParser;
import outputObjects.MatchedWebServiceType;
import outputObjects.ObjectFactory;
import outputObjects.WSMatchingType;
import postGres.PostgreSQLJDBC;

public class Main {

	static boolean isSemantic = true;
	
	public static void main(String[] args) {

		DomParser.domParser(isSemantic);
		
		if(isSemantic) OntologyComparator.initializeOntology();
		
		List<String> ServiceNames = new ArrayList<String>();
		List<String> WSDLNames = new ArrayList<String>();
		File outputXML = new File("./src/Output.xml");
		
		try {
			PostgreSQLJDBC.connection();

			String sql = "SELECT \"WSDLName\",\"ServiceName\" FROM \"Service\";";
			ResultSet result = PostgreSQLJDBC.executeSelectQuery(sql);

			while (result.next()) {
				ServiceNames.add(result.getString("ServiceName"));
				WSDLNames.add(result.getString("WSDLName"));
			}

			if (ServiceNames.isEmpty()) {
				System.out.println("No Service Records exist in Database!");
				return;
			}
	
			ObjectFactory factory = new ObjectFactory();
			WSMatchingType matchingObj = factory.createWSMatchingType();

			for (int i = 0; i < ServiceNames.size(); i++) {//Iterate through all WSDLs
				ServiceDetail serviceDetailOutputObj = GenerateDataObjects.populateWSDLServiceDetail(ServiceNames.get(i),WSDLNames.get(i), "1"); //Populate output WSDL
				for (int j = 0; j < ServiceNames.size(); j++) {
					if(j == i)
					{
						continue;
					}
					ServiceDetail serviceDetailInputObj = GenerateDataObjects.populateWSDLServiceDetail(ServiceNames.get(j),WSDLNames.get(j), "0");
					MatchedWebServiceType serviceObj = WSMatcher.compareWSDLS(serviceDetailOutputObj, serviceDetailInputObj, isSemantic);
					if(serviceObj != null)
						matchingObj.getMatching().add(serviceObj);
				}
			}
			
			if(!matchingObj.getMatching().isEmpty()){
				JAXBMarshallar.marshallerMethod(factory, matchingObj, outputXML);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
	}
}
