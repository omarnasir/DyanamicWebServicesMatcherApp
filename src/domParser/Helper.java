package domParser;

import org.w3c.dom.Node;

import dataObjects.MessageType;
import dataObjects.Tags;

public class Helper {

	public static boolean checkNode(Node obj, String nodeName) {
		return obj.getNodeName().toLowerCase().contains(nodeName);
	}

	public static String getNodeValue(Node obj) {
		if(obj.getAttributes() != null)
		{
			if(obj.getAttributes().getNamedItem(Tags.name.name()) != null)
				return obj.getAttributes().getNamedItem(Tags.name.name()).getNodeValue();
		}
		return "";
	}
	
	public static String getNodeValue(Node obj, String attribute) {
		if(obj.getAttributes() != null)
		{
			if(obj.getAttributes().getNamedItem(attribute) != null)
				return obj.getAttributes().getNamedItem(attribute).getNodeValue();
		}
		return null;
	}

	public static String getMessageName(Node obj) {
		String message = obj.getAttributes().getNamedItem(Tags.message.name()).getNodeValue();
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
