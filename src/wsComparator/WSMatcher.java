package wsComparator;

import java.util.ArrayList;
import java.util.List;

import dataObjects.ServiceDetail;
import outputObjects.*;

public class WSMatcher {
	public static MatchedWebServiceType compareWSDLS(ServiceDetail outputObj, ServiceDetail inputObj) {
		try{
			
			List<Double> containerForOperationScores = new ArrayList<Double>(); //To calculate final service score, stores averages of operations
			MatchedWebServiceType serviceObj = new MatchedWebServiceType(); //JAXB Element for Matched Service 
			
			List<MatchedOperationType> operationList = new ArrayList<MatchedOperationType>(); //List of Matched Operations for this Service
			
			for (int oi = 0; oi < outputObj.getOperations().size(); oi++) {
				ServiceDetail.Operation outputOperationObj = outputObj.getOperations().get(oi); //Get reference to List Operation object for output WSDL
							
				for (int oj = 0; oj < inputObj.getOperations().size(); oj++) {
					List<Double> containerForElementScores = new ArrayList<Double>(); //To calculate final operation score, stores averages of matched elements
					
					ServiceDetail.Operation inputOperationObj = inputObj.getOperations().get(oj); //Get reference to List Operation object for input WSDL				
	
					List<MatchedElementType> elementList = new ArrayList<MatchedElementType>(); //JAXBElement to store matched elements for this operation
					
					for(int ei = 0; ei < outputOperationObj.getElementName().size(); ei++)
					{
						ServiceDetail.Element outputElementObj = outputOperationObj.getElementName().get(ei);//Get reference to List Element object for output operation
						
						for(int ej = 0; ej < inputOperationObj.getElementName().size(); ej++)
						{
							ServiceDetail.Element inputElementObj = inputOperationObj.getElementName().get(ej);//Get reference to List Element object for input Operation
							
							double result = EditDistance.getSimilarity(outputElementObj.getElementName(),inputElementObj.getElementName());
							
							if(result >= 0.8)
							{					
								MatchedElementType elementObj = new MatchedElementType();
								elementObj.setOutputElement(outputElementObj.getElementName());
								elementObj.setInputElement(inputElementObj.getElementName());
								elementObj.setScore(result);
								elementList.add(elementObj);
								containerForElementScores.add(result);
							}
						}
					}				
					if (!elementList.isEmpty()) {
						Double avgOpScore = calculateAverage(containerForElementScores);
						
						MatchedOperationType operationObj = new MatchedOperationType();
						operationObj.setMatchedElement(elementList);
						operationObj.setOutputOperationName(outputOperationObj.getOperationName());
						operationObj.setInputOperationName(inputOperationObj.getOperationName());
						operationObj.setOpScore(avgOpScore);
						
						operationList.add(operationObj);
						containerForOperationScores.add(avgOpScore);
					}
				}
			}
			
			if (!operationList.isEmpty()) {
				Double avgServiceScore = calculateAverage(containerForOperationScores);
				
				serviceObj.setMatchedOperation(operationList);
				serviceObj.setOutputServiceName(outputObj.getServiceName());
				serviceObj.setInputServiceName(inputObj.getServiceName());
				serviceObj.setWsScore(avgServiceScore);
				return serviceObj;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	private static double calculateAverage(List<Double> list)
	{
		Double sum = 0.0;
		for(Double result2 : list)
		{
			sum += result2;
		}
		return sum/list.size();
	}
}
