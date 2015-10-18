package com.surkein.raven;

import com.rainforestautomation.model.Command;
import com.rainforestautomation.model.ConnectionStatus;
import com.rainforestautomation.model.DeviceInfo;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

import javax.xml.bind.UnmarshalException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RavenXmlClient {
    private final SerialPort port;
    private final RavenXmlHandler handler;
    private final Map<Long, String> dataMap;
    private final ScheduledExecutorService passiveReadingDaemon = Executors.newScheduledThreadPool(1);
    private final Map<String, CompletableFuture<Object>> listeningMap;
    private volatile String partialValue = "";
    public RavenXmlClient(String portName) {
        port = new SerialPort("COM3");
        dataMap = new HashMap<Long, String>();
        handler = new RavenXmlHandler();
        listeningMap = new HashMap<String, CompletableFuture<Object>>();
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

        passiveReadingDaemon.scheduleAtFixedRate(new Runnable() {
            public void run() {
                try {
                    String data = port.readString();
                    Long time = System.currentTimeMillis();
                    if (data != null) {
                        System.out.println("Received data at: " + time + " : \r\n" + data);
                        dataMap.put(time, data);
                        FileWriter fw = new FileWriter(new File("D:/Projects/Personal/Rainforest/SampleFiles/" + time + ".xml"));
                        fw.write(data);
                        fw.close();
                        Object o;
                        try {
                            if (partialValue != null && !partialValue.isEmpty()) {
                                System.out.println("Partial data: \r\n" + partialValue);
                                System.out.println("New data: \r\n" + data);
                                data = partialValue + data;
                                partialValue = "";
                            }
                            o = handler.unmarshall(data);
                            if (listeningMap.containsKey(o.getClass().getName())) {
                                CompletableFuture<Object> remove = listeningMap.remove(o.getClass().getName());
                                remove.complete(o);
                            }
                        } catch (RuntimeException e) {
                            if (e.getCause() instanceof UnmarshalException) {
                                partialValue = data;
                            }
                        }
                    }
                } catch (SerialPortException | IOException e) {
                    e.printStackTrace();
                }
            }
        }, 1, 1, TimeUnit.SECONDS);

    }

    public CompletableFuture<Object> getConnectionStatus() {
        writeCommand(Command.Commands.get_connection_status);
        writeCommand(Command.Commands.get_device_info);
        CompletableFuture<Object> connectionStatusCompletableFuture = new CompletableFuture<Object>();
        listeningMap.put(ConnectionStatus.class.getName(), connectionStatusCompletableFuture);
        return connectionStatusCompletableFuture;
    }

    public CompletableFuture<Object> getDeviceInfo() {
        writeCommand(Command.Commands.get_device_info);
        CompletableFuture<Object> deviceInfoCompletableFuture = new CompletableFuture<Object>();
        listeningMap.put(DeviceInfo.class.getName(), deviceInfoCompletableFuture);
        return deviceInfoCompletableFuture;
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
