package com.surkein.raven;

import com.surkein.raven.model.Command;
import com.surkein.raven.model.ConnectionStatus;
import com.surkein.raven.model.DeviceInfo;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

public class RavenXmlClient {
    private final SerialPort port;
    private JAXBContext jaxContext;
    private Marshaller marshaller;
    private Unmarshaller unmarshaller;
    public RavenXmlClient(String portName) {
        port = new SerialPort("COM3");
        try {
            port.openPort();
            port.setParams(SerialPort.BAUDRATE_115200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
            port.setEventsMask(511);
            port.addEventListener(new SerialPortEventListener() {
                public void serialEvent(SerialPortEvent serialPortEvent) {
                    if(serialPortEvent.isERR()) {
                        System.out.println("ERROR: " + serialPortEvent.getEventValue());
                    }
                    if(serialPortEvent.isRLSD()) {
                        System.out.println("RLSD: " + serialPortEvent.getEventValue());
                    }
                    if(serialPortEvent.isRING()) {
                        System.out.println("RING: " + serialPortEvent.getEventValue());
                    }
                }
            });
            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                public void run() {
                    try {
                        if(port.isOpened()) {
                            port.closePort();
                        }
                    } catch (SerialPortException e) {
                        e.printStackTrace();
                    }
                }
            }));
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
        try {
            jaxContext = JAXBContext.newInstance(Command.class, ConnectionStatus.class);
            marshaller = jaxContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "Windows-1252");
            unmarshaller = jaxContext.createUnmarshaller();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
    public ConnectionStatus getConnectionStatus() {
        synchronized (port) {
            writeCommand(Command.Commands.get_connection_status);
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return readObject(ConnectionStatus.class);
        }
    }

    public DeviceInfo getDeviceInfo() {
        synchronized (port) {
            writeCommand(Command.Commands.get_device_info);
            return readObject(DeviceInfo.class);
        }
    }

    private <T> T readObject(Class<T> clazz) {
        try {
            String inputString = port.readString();
            System.out.println(inputString);
            return (T) unmarshaller.unmarshal(new StringReader(inputString));
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void writeCommand(Command.Commands command) {
        StringWriter stringWriter = new StringWriter();
        try {
            marshaller.marshal(command.getCommand(), stringWriter);
            String commandXml = stringWriter.toString();
            if(!commandXml.endsWith("\r\n")) {
                commandXml += "\r\n";
            }
            port.writeString(commandXml);
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }
    public void close() {
        try {
            port.closePort();
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }
}
