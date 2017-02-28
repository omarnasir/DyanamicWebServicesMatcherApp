package domParser;

import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import dataObjects.MessageType;

public class Helper {
	public static List<String> differentiatorSequenceComplexTypes(List<String> result, Node seq) {
		for (int j = 0; j <seq.getChildNodes().getLength(); j++)
		{
			if (checkNode(seq.getChildNodes().item(j), "element")) {
				result = Elements.getElement(seq.getChildNodes().item(j), result);
			}
		}
		return result;
	}

	public static boolean checkNode(Node obj, String nodeName) {
		return obj.getNodeName().toLowerCase().contains(nodeName);
	}

	public static String getNodeValue(Node obj) {
		if(obj.getAttributes().getNamedItem("name") != null)
			return obj.getAttributes().getNamedItem("name").getNodeValue();// .getNamedItem("name").getNodeValue();
		return "";
	}

	public static String getMessageName(Node obj) {
		String message = obj.getAttributes().getNamedItem("message").getNodeValue();
		int pos = message.indexOf(':');
		return message.substring(pos + 1);
	}
	
	public static MessageType getElementNamefromMessage(Node node) {
		MessageType obj = new MessageType();
		
		String value = null;
		boolean isElement = true;
		Node nd = node.getAttributes().getNamedItem("element");
		if (nd != null) {
			value = nd.getNodeValue();
		} else {
			nd = node.getAttributes().getNamedItem("name");
			if (nd != null) {
				value = nd.getNodeValue();
				isElement = false;
			}
		}
		
		int pos = value.indexOf(':');
		if(isElement)
			obj.setElement(value.substring(pos + 1));
		else
			obj.setName(value.substring(pos + 1));
		return obj;
	}
	
	public static String getPartTypefromMessage(Node node) {	
		String value = null;
		
		Node nd = node.getAttributes().getNamedItem("type");
		if (nd != null) {
			value = nd.getNodeValue();
		} else {
			nd = node.getAttributes().getNamedItem("element");
			if (nd != null) {
				value = nd.getNodeValue();
			}
		}
		return value.substring(value.indexOf(':') + 1);
	}
}
