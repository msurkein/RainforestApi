package com.surkein.raven;

import com.surkein.raven.model.Command;
import com.surkein.raven.model.ConnectionStatus;
import com.surkein.raven.model.DeviceInfo;
import javafx.concurrent.ScheduledService;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RavenXmlClient {
    private final SerialPort port;
    private final RavenXmlHandler handler;
    private final Map<Long, String> dataMap;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    public RavenXmlClient(String portName) {
        port = new SerialPort("COM3");
        dataMap = new HashMap<Long, String>();
        handler = new RavenXmlHandler();
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

        scheduler.scheduleAtFixedRate(new Runnable() {
            public void run() {
                try {
                    String data = port.readString();
                    Long time = System.currentTimeMillis();
                    if(data != null) {
                        System.out.println("Received data at: " + time + " : \r\n" + data);
                        dataMap.put(time, data);
                        FileWriter fw = new FileWriter(new File("D:/Projects/Personal/Rainforest/SampleFiles/" + time + ".xml"));
                        fw.write(data);
                        fw.close();
                    }
                    else {
                        //System.out.println("No new data");
                    }
                } catch (SerialPortException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 1, 1, TimeUnit.SECONDS);
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
            return (T) handler.unmarshall(inputString);
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void writeCommand(Command.Commands command) {
        try {
            String commandXml = handler.marshal(command.getCommand());
            if(!commandXml.endsWith("\r\n")) {
                commandXml += "\r\n";
            }
            port.writeString(commandXml);
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
