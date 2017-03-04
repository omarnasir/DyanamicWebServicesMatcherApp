package domParser;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import dataObjects.Tags;
import javafx.util.Pair;
import postGres.SQLScripts;

public class PortTypes {
	public static void parsePortTypes(List<Node> portType, List<Node> bindingList) {
		if (!portType.isEmpty()) {
			for(int i=0; i< portType.size();i++) //iterate through <portType> Nodes List
			{
			NodeList listObjPortType = portType.get(i).getChildNodes(); //get Node corresponding to <portType>

			for (int j = 0; j < listObjPortType.getLength(); j++) // Iterate through child of <portType> Node
			{
				if (Helper.checkNode(listObjPortType.item(j), Tags.operation.name())) { //Find <operation> tag inside <portType>

					String operationName = Helper.getNodeValue(listObjPortType.item(j)); //Extract <operation> "name" parameter
					List<Pair<String,String>> elementOpList = new ArrayList<Pair<String,String>>();
					
					for(Node bindingChild : bindingList) //iterate through <binding> Nodes list
					{
						for(int a=0; a<bindingChild.getChildNodes().getLength(); a++) //Get child of <binding> Node
						{
							if(Helper.checkNode(bindingChild.getChildNodes().item(a),"operation")) //Find <operation> inside <binding>
							{
								Node bindingChildofChild = bindingChild.getChildNodes().item(a); //<operation>
								if(Helper.getNodeValue(bindingChildofChild).equals(operationName)){ //operation Found
									for (int b=0; b<bindingChildofChild.getChildNodes().getLength(); b++){ //get child of <operation>
										
										NodeList op = bindingChildofChild.getChildNodes(); 
										for(int index =0; index < op.item(b).getChildNodes().getLength();index++){ //get children of either <input> or <output>
											if(Helper.checkNode(op.item(b).getChildNodes().item(index), "header")){ //find <soap:header> inside input
												
												if(Helper.checkNode(op.item(b).getChildNodes().item(0).getParentNode(),"input")){
												elementOpList.add(new Pair<String,String>(Helper.getMessageName(op.item(b).getChildNodes().item(index)),"0"));
												}
												else if(Helper.checkNode(op.item(b).getChildNodes().item(0).getParentNode(),"output")){
													elementOpList.add(new Pair<String,String>(Helper.getMessageName(op.item(b).getChildNodes().item(index)),"1"));
												}
											}
										}
									}
								}
							}
						}
					}
					
					NodeList listObjOperationType = listObjPortType.item(j).getChildNodes(); //get children of <portType>

					for (int k = 0; k < listObjOperationType.getLength(); k++) { //get inside <portType> Node

						if (Helper.checkNode(listObjOperationType.item(k), "input")) { 
							elementOpList.add(new Pair<String,String>(Helper.getMessageName(listObjOperationType.item(k)),"0"));
						} else if (Helper.checkNode(listObjOperationType.item(k), "output")) {
							elementOpList.add(new Pair<String,String>(Helper.getMessageName(listObjOperationType.item(k)),"1"));
						}
					}
					
					//Use Lists here
					for(int k=0;k<elementOpList.size();k++)
					{
						SQLScripts.operationQuery(operationName, elementOpList.get(k).getKey(), elementOpList.get(k).getValue());
					}
				}
			}
		}
		}
	}

}
