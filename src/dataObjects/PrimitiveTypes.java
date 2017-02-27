package dataObjects;

import org.w3c.dom.Node;

public class PrimitiveTypes {
	private static String[] primitives = { "string", "integer", "int", "long", "short", "decimal", "float", "double",
			"boolean", "byte", "QName", "dateTime", "base64Binary", "hexBinary", "unsignedInt", "unsignedShort",
			"unsignedByte", "time", "date", "g", "anySimpleType", "anySimpleType", "duration", "NOTATION" };

	
	private static boolean isPrimitive(String input)
	{
		for(String type : primitives)
		{
			if(input.contains(type))
				return true;
		}
		return false;
	}
	
	public static boolean checkPrimitiveTypes(Node nodeObj)
	{
		String type = nodeObj.getAttributes().getNamedItem("type").getNodeValue();
		int pos = type.indexOf(':');
		boolean result = isPrimitive(type.substring(pos+1));
		return result;
	}
	
}
