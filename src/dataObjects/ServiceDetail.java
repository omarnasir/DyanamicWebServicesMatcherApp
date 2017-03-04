package dataObjects;

import java.util.ArrayList;
import java.util.List;

public class ServiceDetail {
	protected String WSDLName;
	protected String ServiceName;
	protected List<Operation> Operations;

	public String getServiceName() {
		return ServiceName;
	}

	public void setServiceName(String serviceName) {
		ServiceName = serviceName;
	}

	public List<Operation> getOperations() {
		if(Operations == null)
		{
			Operations = new ArrayList<Operation>();
		}
		return Operations;
	}

	public void setOperations(List<Operation> operations) {
		Operations = operations;
	}

	public String getWSDLName() {
		return WSDLName;
	}

	public void setWSDLName(String WSDLName) {
		this.WSDLName = WSDLName;
	}

	public static class Operation{
		protected String OperationName;
		protected List<Element> ElementName;
		
		public String getOperationName() {
			return this.OperationName;
		}

		public void setOperationName(String operationName) {
			this.OperationName = operationName;
		}

		public List<Element> getElementName() {
			if(ElementName == null)
			{
				ElementName = new ArrayList<Element>();
			}
			return ElementName;
		}

		public void setElementName(List<Element> elementName) {
			ElementName = elementName;
		}

	}
	
	public static class Element{
		protected String ElementName;
		protected String Annotation;
		
		public String getElementName() {
			return ElementName;
		}

		public void setElementName(String elementName) {
			ElementName = elementName;
		}

		public String getAnnotation() {
			return Annotation;
		}

		public void setAnnotation(String annotation) {
			Annotation = annotation;
		}
	}
}
