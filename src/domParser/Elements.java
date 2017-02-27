package domParser;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;

import dataObjects.PrimitiveTypes;

public class Elements {
	public static List<String> getElement(Node list, List<String> result) {
		if (result == null) {
			result = new ArrayList<String>();
		}
		try{
			if (Helper.checkNode(list, "element")) { // If Element found
				if (list.getAttributes().getNamedItem("type") != null) { // check if type is defined here or not
					boolean check = PrimitiveTypes.checkPrimitiveTypes(list);
					if (check) {
						result.add(Helper.getNodeValue(list));
					}
					return result;
				}
				else{	// go inside
					for (int i = 0; i < list.getChildNodes().getLength(); i++) { //child nodes of <element> without <type>
						if (Helper.checkNode(list.getChildNodes().item(i), "complextype")) { // get <complexType>
							Node obj = list.getChildNodes().item(i); //node for complexType 
							for (int k = 0; k < obj.getChildNodes().getLength(); k++) { // iterate through <complexType>  children
								if (Helper.checkNode(obj.getChildNodes().item(k), "sequence")) {
									obj = obj.getChildNodes().item(k); //get <sequence>
									result = Helper.differentiatorSequenceComplexTypes(result, obj);
									}
							}
						}
						else if (Helper.checkNode(list.getChildNodes().item(i), "sequence")) {
							Node seq = list.getChildNodes().item(i);
							result = Helper.differentiatorSequenceComplexTypes(result, seq);
						}
					}
				}
			}
			return result;
		}
		catch (Exception e)
		{
			result.add("error");
			return result;
		}
	}
}
