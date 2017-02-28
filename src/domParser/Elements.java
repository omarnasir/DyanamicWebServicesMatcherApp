package domParser;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.util.Types;

import org.w3c.dom.Node;

import com.sun.glass.ui.CommonDialogs.Type;

import dataObjects.PrimitiveTypes;
import dataObjects.Tags;
import javafx.util.Pair;
import postGres.SQLScripts;

public class Elements {
	
	private static String value = "";
	public static List<String> getElement(Node list, List<String> result) {
		if (result == null) {
			result = new ArrayList<String>();
		}
		try{
			if(list.getAttributes() != null){
				if (list.getAttributes().getNamedItem(Tags.type.name()) != null) { // check if type is defined here or not
					boolean check = PrimitiveTypes.checkPrimitiveTypes(list);
					if (check) {
						result.add(Helper.getNodeValue(list));
					}
					return result;
				}
				else{	// go inside
					for (int i = 0; i < list.getChildNodes().getLength(); i++) { //child nodes of <element> without <type>
						result = Elements.getElement(list.getChildNodes().item(i), result);
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
	
	public static List<Pair<String,String>> getAnnotationList(Node node, Node schema, List<Pair<String,String>> annotationList)
	{
		if (annotationList == null) {
			annotationList = new ArrayList<Pair<String,String>>();
		}
		Pair<String,String> pairObj = checkAnnotation(node);
		if(pairObj != null){
			annotationList.add(pairObj);
			return annotationList;
		}
		else{
			if(Helper.getNodeValue(node, Tags.type.name()) != null){
				value = Helper.getNodeValue(node, Tags.type.name());
				value = value.substring(value.indexOf(":") + 1);
				getAnnotationListfromSchema(schema, annotationList);
			}
			else{
				for (int i = 0; i < node.getChildNodes().getLength(); i++) { //child nodes 
					getAnnotationList(node.getChildNodes().item(i),schema, annotationList);
				}
			}
		}
		return annotationList;
	}

	public static List<Pair<String,String>> getAnnotationListfromSchema(Node schema, List<Pair<String,String>> annotationList)
	{
		for(int i=0; i<schema.getChildNodes().getLength(); i++)
		{
			if(schema.getChildNodes().item(i).getNodeName().toLowerCase().contains(Tags.type.name())){
				if (Helper.getNodeValue(schema.getChildNodes().item(i)).toLowerCase().contentEquals(value.toLowerCase())) {
					getAnnotationList(schema.getChildNodes().item(i),schema,annotationList);
				}
			}
		}
		return annotationList;
	}
	
	private static Pair<String,String> checkAnnotation(Node node) {
		Pair<String,String> pairObj = null;
		if(node.getAttributes() != null){
			Node obj = node.getAttributes().getNamedItem("sawsdl:modelReference");
			if (obj != null) { // check if annotation exists here or not
				pairObj = new Pair<String, String>(Helper.getNodeValue(node), obj.getNodeValue().substring(obj.getNodeValue().indexOf("#")+1));
			}
		}
		return pairObj;
	}
}
