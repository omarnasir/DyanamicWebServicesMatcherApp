package wsdlcomparator;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import postGres.PostgreSQLJDBC;

public class DomParser {

	public static boolean domParserMethod(File fileObj) {

		String sql = null;
		boolean check = false;

		try {

			PostgreSQLJDBC.connection();

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fileObj);
			doc.getDocumentElement().normalize();

			NodeList listObj = doc.getDocumentElement().getChildNodes();

			for (int i = 0; i < listObj.getLength(); i++) {
				if (listObj.item(i).getNodeName().toLowerCase().contains("service")) {
					String serviceName = getNodeValue(listObj, i);

					if (check)
						System.out.println(serviceName);
					sql = "INSERT INTO \"Service\" (\"ServiceID\",\"ServiceName\") "
							+ "VALUES (nextval('\"seqService\"'), '" + serviceName + "' );";
					PostgreSQLJDBC.executeInsertQuery(sql);
				}
			}
			for (int i = 0; i < listObj.getLength(); i++) { // portType
				if (checkNode(listObj, i, "porttype")) {

					NodeList listObjPortType = listObj.item(i).getChildNodes();

					for (int j = 0; j < listObjPortType.getLength(); j++) // operations
					{
						if (checkNode(listObjPortType, j, "operation")) {

							String operationName = getNodeValue(listObjPortType, j);
							if (check)
								System.out.println(operationName);

							NodeList listObjOperationType = listObjPortType.item(j).getChildNodes();

							for (int k = 0; k < listObjOperationType.getLength(); k++) {
								if (checkNode(listObjOperationType, k, "input")) {
									String inputMessageName = getMessageName(listObjOperationType, k);
									if (check)
										System.out.println(inputMessageName);

									sql = "INSERT INTO \"Operation\" (\"OperationID\",\"ServiceID\",\"OperationName\",\"MessageName\",\"OperationType\") "
											+ "VALUES (nextval('\"seqOperation\"'),currval('\"seqService\"'), '"
											+ operationName + "', '" + inputMessageName + "','" + 0 + "' );";
									PostgreSQLJDBC.executeInsertQuery(sql);

								} else if (checkNode(listObjOperationType, k, "output")) {

									String outputMessageName = getMessageName(listObjOperationType, k);
									if (check)
										System.out.println(outputMessageName);

									sql = "INSERT INTO \"Operation\" (\"OperationID\",\"ServiceID\",\"OperationName\",\"MessageName\",\"OperationType\") "
											+ "VALUES (nextval('\"seqOperation\"'),currval('\"seqService\"'), '"
											+ operationName + "', '" + outputMessageName + "','" + 1 + "' );";
									PostgreSQLJDBC.executeInsertQuery(sql);
								}
							}
						}
					}
				}
			}

			for (int i = 0; i < listObj.getLength(); i++) { // messages
				if (checkNode(listObj, i, "message")) {
					String message = getNodeValue(listObj, i);
					if (check)
						System.out.println(message);
					NodeList listObjMessagePart = listObj.item(i).getChildNodes();

					for (int j = 0; j < listObjMessagePart.getLength(); j++) {

						if (checkNode(listObjMessagePart, j, "part")) {
							String element = getElementName(listObjMessagePart, j);
							if (element != null) {
								sql = "INSERT INTO \"Element\" (\"ElementID\",\"OperationID\",\"ElementName\") "
										+ "VALUES (nextval('\"seqElement\"'),(SELECT \"OperationID\" FROM \"Operation\" "
										+ "WHERE \"MessageName\" = '" + message + "'), '" + element + "');";
								PostgreSQLJDBC.executeInsertQuery(sql);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if (check)
				e.printStackTrace();
			return false;
		}
		return true;
	}

	private static boolean checkNode(NodeList list, int index, String nodeName) {
		return list.item(index).getNodeName().toLowerCase().contains(nodeName);
	}

	private static String getNodeValue(NodeList list, int index) {
		return list.item(index).getAttributes().getNamedItem("name").getNodeValue();
	}

	private static String getMessageName(NodeList list, int index) {
		String message = list.item(index).getAttributes().getNamedItem("message").getNodeValue();
		// System.out.println(message);
		int pos = message.indexOf(':');
		return message.substring(pos + 1);
	}

	private static String getElementName(NodeList list, int index) {

		Node nd = list.item(index).getAttributes().getNamedItem("element");
		if (nd != null) {
			String value = list.item(index).getAttributes().getNamedItem("element").getNodeValue();
			int pos = value.indexOf(':');
			return value.substring(pos + 1);
		}
		nd = list.item(index).getAttributes().getNamedItem("name");
		if (nd != null) {
			String value = list.item(index).getAttributes().getNamedItem("name").getNodeValue();
			int pos = value.indexOf(':');
			return value.substring(pos + 1);
		}
		return null;
	}
}