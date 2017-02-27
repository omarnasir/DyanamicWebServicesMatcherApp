package domParser;

import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import postGres.SQLScripts;

public class PortTypes {
	public static void parsePortTypes(List<Node> portType) {
		if (!portType.isEmpty()) {
			for(int i=0; i< portType.size();i++)
			{
			NodeList listObjPortType = portType.get(i).getChildNodes();

			for (int j = 0; j < listObjPortType.getLength(); j++) // operations
			{
				if (Helper.checkNode(listObjPortType.item(j), "operation")) {

					String operationName = Helper.getNodeValue(listObjPortType.item(j));
					//System.out.println(operationName);

					NodeList listObjOperationType = listObjPortType.item(j).getChildNodes();

					for (int k = 0; k < listObjOperationType.getLength(); k++) {
						
						String messageName = null;
						int operationType = 0;
						if (Helper.checkNode(listObjOperationType.item(k), "input")) {
							messageName = Helper.getMessageName(listObjOperationType, k);
						} else if (Helper.checkNode(listObjOperationType.item(k), "output")) {
							messageName = Helper.getMessageName(listObjOperationType, k);
							operationType = 1;
						}
						if( messageName != null){
							SQLScripts.operationQuery(operationName, messageName, operationType);
						}
					}
				}
			}
		}
		}
	}

}
