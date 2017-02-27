package domParser;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import dataObjects.MessageType;
import postGres.SQLScripts;

public class Methods {
	public static void parseMethods(NodeList listObj, Node schema, String wsdlName) {
		for (int m = 0; m < listObj.getLength(); m++) { // Find <message>
														// tags
			if (Helper.checkNode(listObj.item(m), "message")) {
				NodeList listObjMessagePart = listObj.item(m).getChildNodes();

				for (int j = 0; j < listObjMessagePart.getLength(); j++) { // Iterate through <message>

					if (Helper.checkNode(listObjMessagePart.item(j), "part")) { // Find <part> tags
						MessageType messageObj = Helper.getElementNamefromMessage(listObjMessagePart, j); // get <Element> or <name>

						if(messageObj.getName() != null){
							SQLScripts.elementQuery(Helper.getNodeValue(listObj.item(m)), messageObj.getName(), wsdlName);
						}
						else {
						List<String> elements = new ArrayList<String>();
						for (int i = 0; i < schema.getChildNodes().getLength(); i++) { // Iterate through <element> inside Schema

							if (Helper.checkNode(schema.getChildNodes().item(i), "element")) { // If element found
								if (Helper.getNodeValue(schema.getChildNodes().item(i)).toLowerCase().contentEquals(messageObj.getElement().toLowerCase())) {
									elements = Elements.getElement(schema.getChildNodes().item(i), elements);
									if (!elements.isEmpty()) {
										
										for (int k = 0; k < elements.size(); k++) {
											if(!elements.get(k).contains("error"))
											{
												SQLScripts.elementQuery(Helper.getNodeValue(listObj.item(m)), elements.get(k), wsdlName);
											}
										}
									}
								}
							}
						}
						}
					}
				}
			}
		}
	}

}
