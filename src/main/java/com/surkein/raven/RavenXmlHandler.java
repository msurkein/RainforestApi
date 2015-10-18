package com.surkein.raven;

import com.surkein.raven.model.Command;
import com.surkein.raven.model.ConnectionStatus;
import com.surkein.raven.model.InstantaneousDemand;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

public class RavenXmlHandler {
    private JAXBContext jaxContext;
    private Marshaller marshaller;
    private Unmarshaller unmarshaller;
    public RavenXmlHandler() {
        try {
            jaxContext = JAXBContext.newInstance(Command.class, ConnectionStatus.class, InstantaneousDemand.class);
            marshaller = jaxContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "Windows-1252");
            unmarshaller = jaxContext.createUnmarshaller();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public <T> T unmarshall(String inputString) {
        try {
            return (T) unmarshaller.unmarshal(new StringReader(inputString));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String marshal(Object command) {
        StringWriter output = new StringWriter();
        try {
            marshaller.marshal(command, output);
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return output.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }
}
