package wsComparator;

import java.io.File;

import javax.xml.bind.*;
import outputObjects.*;

public class JAXBMarshallar {
	public static boolean marshallerMethod(ObjectFactory factory, WSMatchingType matchingObj, File outputXML)
	{
		try {
			JAXBElement<WSMatchingType> element = factory.createWSMatching(matchingObj);
			
			JAXBContext jaxbContext = JAXBContext.newInstance(matchingObj.getClass());
			
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(element, outputXML);
			return true;
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
