package domParser;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import dataObjects.MessageType;
import dataObjects.Tags;
import javafx.util.Pair;
import postGres.SQLScripts;

public class Messages {
	public static void parseMessagesSyntactic(NodeList listObj, Node schema, String wsdlName) {
		for (int m = 0; m < listObj.getLength(); m++) { // Find <message>
														// tags
			if (Helper.checkNode(listObj.item(m),Tags.message.name())) {
				NodeList listObjMessagePart = listObj.item(m).getChildNodes();

				for (int j = 0; j < listObjMessagePart.getLength(); j++) { // Iterate through childNodes of <message>

					if (Helper.checkNode(listObjMessagePart.item(j),Tags.part.name())) { // Find <part> tags	
						MessageType messageObj = Helper.getElementNamefromMessage(listObjMessagePart.item(j)); // get <Element> or <name>
						
						if(messageObj.getName() != null){ //if <name> found :: means no <element> associated with this <message>
							SQLScripts.elementQuery(Helper.getNodeValue(listObj.item(m)), messageObj.getName(), wsdlName);
						}
						else {
							List<String> elements = new ArrayList<String>();
							for (int i = 0; i < schema.getChildNodes().getLength(); i++) { // Iterate through <element> inside Schema
	
								if (Helper.checkNode(schema.getChildNodes().item(i),Tags.element.name())) { // If <element> found :: this is to avoid iterating through <complextype>
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
	public static void parseMessagesSemantic(NodeList listObj, Node schema, String wsdlName) {
		for (int m = 0; m < listObj.getLength(); m++) { // Find <message> tags
			if (Helper.checkNode(listObj.item(m),Tags.message.name())) {
				NodeList listObjMessagePart = listObj.item(m).getChildNodes();
				for (int j = 0; j < listObjMessagePart.getLength(); j++) { // Iterate through childNodes of <message>
					if (Helper.checkNode(listObjMessagePart.item(j),Tags.part.name())) { // Find <part> tags	
						//Get Part Type name
						String value = Helper.getPartTypefromMessage(listObjMessagePart.item(j));
						if(value == null) return;
						List<Pair<String, String>> annotationList = new ArrayList<Pair<String,String>>();
						for (int i = 0; i < schema.getChildNodes().getLength(); i++) {
							if(schema.getChildNodes().item(i).getAttributes() != null){ // Iterate through <element> inside Schema
								if (Helper.getNodeValue(schema.getChildNodes().item(i)).toLowerCase().contentEquals(value.toLowerCase())) {
									annotationList = Elements.getAnnotationList(schema.getChildNodes().item(i), annotationList);
									for(int k =0; k<annotationList.size(); k++){
										SQLScripts.annotationQuery(Helper.getNodeValue(listObj.item(m)), annotationList.get(k).getKey(),annotationList.get(k).getValue(), wsdlName);
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
