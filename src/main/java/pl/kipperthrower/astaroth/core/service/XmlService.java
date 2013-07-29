package pl.kipperthrower.astaroth.core.service;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class XmlService {

	private static final Logger LOGGER = Logger.getLogger(XmlService.class);

	@SuppressWarnings("unchecked")
	public <T> T unmarshall(Class<T> clazz, File file) {
		try {
			JAXBContext jc = JAXBContext.newInstance(clazz);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			return (T) unmarshaller.unmarshal(new StreamSource(file));
		} catch (JAXBException e) {
			LOGGER.error(e, e);
			return null;
		}
	}

	public <T> void marshall(T object, File file) {
		try {
			JAXBContext jc = JAXBContext.newInstance(object.getClass());
			Marshaller marshaller = jc.createMarshaller();
			marshaller.marshal(object, file);
		} catch (JAXBException e) {
			LOGGER.error(e, e);
		}
	}

}
