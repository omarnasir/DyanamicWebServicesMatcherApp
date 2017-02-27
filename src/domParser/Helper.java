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
		return obj.getAttributes().getNamedItem("name").getNodeValue();// .getNamedItem("name").getNodeValue();
	}

	public static String getMessageName(NodeList list, int index) {
		String message = list.item(index).getAttributes().getNamedItem("message").getNodeValue();
		int pos = message.indexOf(':');
		return message.substring(pos + 1);
	}

	public static MessageType getElementNamefromMessage(NodeList list, int index) {
		MessageType obj = new MessageType();
		
		String value = null;
		boolean isElement = true;
		Node nd = list.item(index).getAttributes().getNamedItem("element");
		if (nd != null) {
			value = nd.getNodeValue();
		} else {
			nd = list.item(index).getAttributes().getNamedItem("name");
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
}
