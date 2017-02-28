package domParser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import postGres.PostgreSQLJDBC;
import postGres.SQLScripts;

public class DomParser {

	public static void domParser(boolean isSemantic) {
		
		PostgreSQLJDBC.connection();
		SQLScripts.deleteDatabase();
		
		String path = "/WSDL";
		if(isSemantic) path = "/SAWSDL";
		
		File folder = new File("src/resources" + path);
		File[] listOfFiles = folder.listFiles();
		
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile() && listOfFiles[i].getName().contains(".wsdl")) {
				boolean result = domParserMethod(listOfFiles[i], isSemantic);
				System.out.println("File " + listOfFiles[i].getName() + "; Result: " + result);
			}
		}
	}

	private static boolean domParserMethod(File fileObj, boolean isSemantic) {

		try {			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			dbFactory.setIgnoringElementContentWhitespace(true);
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fileObj);
			doc.getDocumentElement().normalize();

			NodeList listObj = doc.getDocumentElement().getChildNodes();

			Node service = null;
			List<Node> portType = new ArrayList<Node>();
			List<Node> schemaList = new ArrayList<Node>();
			List<Node> bindingList = new ArrayList<Node>();
			
			for (int i = 0; i < listObj.getLength(); i++) {
				String temp = listObj.item(i).getNodeName().toLowerCase();
				if (temp.contains("service")) {
					service = listObj.item(i);
				} else if (temp.contains("porttype")) {
					Node port = listObj.item(i);
					portType.add(port);
				} else if (temp.contains("binding")) {
					Node binding = listObj.item(i);
					bindingList.add(binding);
				} else if (temp.contains("types")) {
					for (int j = 0; j < listObj.item(i).getChildNodes().getLength(); j++) {
						if (Helper.checkNode(listObj.item(i).getChildNodes().item(j), "schema")) {
							schemaList.add(listObj.item(i).getChildNodes().item(j));
						}
					}
				}
			}
			if(service != null && !portType.isEmpty() && !schemaList.isEmpty()){
				String serviceName = Helper.getNodeValue(service);
				//System.out.println(serviceName);
				SQLScripts.serviceQuery(serviceName, fileObj.getName());
				PortTypes.parsePortTypes(portType, bindingList);
				for (Node schema : schemaList){
					Messages.parseMessages(listObj, schema, fileObj.getName(), isSemantic);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
}