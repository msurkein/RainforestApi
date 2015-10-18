# RainforestApi
## Purpose
This project aims to provide support for the Rainforest Automation RAVEn XML API.  
More information about the API can be found here: http://rainforestautomation.com/developer/

## Usage
To use this library, simply create an instance of a RavenXmlClient class and use the methods on it to interact with the device.
```java
RavenXmlClient client = new RavenXmlClient("COM1");
CompletableFuture<Object> info = client.getDeviceInfo();
DeviceInfo deviceInfo = (DeviceInfo) info.get();
System.out.println(deviceInfo.getDeviceMacId);
```

## Future
The library is very limited currently but in the future it will expand to support the entire RAVEn XML API.  Right now it only supports listening for broadcasted events from the device.  Sending messages to the device works sporadically.

## Libraries Used
Serial port support is provided by jssc - https://github.com/scream3r/java-simple-serial-connector
Guava - https://github.com/google/guava 