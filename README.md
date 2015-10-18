# RainforestApi
This project aims to provide support for the Rainforest Automation RAVEn XML API.  
More information about the API can be found here: http://rainforestautomation.com/developer/

To use this library, simply create an instance of a RavenXmlClient class and use the methods on it to interact
with the device.

Currently, the library is very limited but in the future it will support the entire RAVEn XML API.  Right now it only
supports listening for broadcasted events from the device.

Serial port support is provided by jssc - https://github.com/scream3r/java-simple-serial-connector