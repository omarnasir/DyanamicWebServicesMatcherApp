package wsComparator;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import dataObjects.ServiceDetail;

public class WSMatcher {
	public static boolean compareWSDLS(ServiceDetail outputObj, ServiceDetail inputObj) {
		DecimalFormat numberFormat = new DecimalFormat("#.00");
		
		List<Double> serviceScore = new ArrayList<Double>();
		
		for (int oi = 0; oi < outputObj.getOperations().size(); oi++) {
			ServiceDetail.Operation outputOperationObj = outputObj.getOperations().get(oi); //Get reference to List Operation object for output WSDL
						
			for (int oj = 0; oj < inputObj.getOperations().size(); oj++) {
				List<Double> operationScore = new ArrayList<Double>();
				
				ServiceDetail.Operation inputOperationObj = inputObj.getOperations().get(oj); //Get reference to List Operation object for input WSDL				

				for(int ei = 0; ei < outputOperationObj.getElementName().size(); ei++)
				{

					ServiceDetail.Element outputElementObj = outputOperationObj.getElementName().get(ei);//Get reference to List Element object for output operation
					
					for(int ej = 0; ej < inputOperationObj.getElementName().size(); ej++)
					{
						ServiceDetail.Element inputElementObj = inputOperationObj.getElementName().get(ej);//Get reference to List Element object for input Operation
						
						double result = EditDistance.getSimilarity(outputElementObj.getElementName(),inputElementObj.getElementName());
						
						if(result >= 0.8)
						{
							System.out.print("		Output Element #" + ei + ": " + outputElementObj.getElementName());
							System.out.print("	-	Input Element #" + ej + ": " + inputElementObj.getElementName());
							System.out.println("	-	" + numberFormat.format(result));
							
							operationScore.add(result);
						}
					}
				}
				Double sum = calculateAverage(operationScore);
				if (sum > 0.0) {
					System.out.print("	Output Operation: " + outputOperationObj.getOperationName());
					System.out.print("	-	Input Operation: " + inputOperationObj.getOperationName());
					System.out.println("	-	" + numberFormat.format(sum));
					serviceScore.add(sum / operationScore.size());
				}
			}
		}
		Double sum = calculateAverage(serviceScore);
		if (sum > 0.0) {
			System.out.print("Output Service: " + outputObj.getServiceName());
			System.out.print("	-	Input Service: " + inputObj.getServiceName());
			System.out.println("	-	" + numberFormat.format(sum));
			serviceScore.add(sum / serviceScore.size());
		}
		return false;
	}
	
	private static double calculateAverage(List<Double> list)
	{
		Double sum = 0.0;
		if(list.isEmpty()){
			return sum;
		}

		for(Double result2 : list)
		{
			sum += result2;
		}
		return sum/list.size();
	}
}
