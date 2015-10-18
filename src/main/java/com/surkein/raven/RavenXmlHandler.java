package com.surkein.raven;

import com.rainforestautomation.model.*;

import javax.xml.bind.*;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

public class RavenXmlHandler {
    private JAXBContext jaxContext;
    private Marshaller marshaller;
    private Unmarshaller unmarshaller;
    public RavenXmlHandler() {
        try {
            jaxContext = JAXBContext.newInstance(Command.class, ConnectionStatus.class, InstantaneousDemand.class, DeviceInfo.class, CurrentSummationDelivered.class);
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
            if (e instanceof UnmarshalException) {
                throw new RuntimeException(e);
            } else {
                e.printStackTrace();
            }
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
