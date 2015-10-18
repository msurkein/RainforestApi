package com.rainforestautomation.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="DeviceInfo")
public class DeviceInfo {
    private String deviceMacId;
    private String installCode;
    private String linkKey;
    private String firmwareVersion;
    private String hardwareVersion;
    private String imageType;
    private String manufacturer;
    private String modelId;
    private String dateCode;

    @XmlElement(name="DeviceMacId")
    public String getDeviceMacId() {
        return deviceMacId;
    }

    public void setDeviceMacId(String deviceMacId) {
        this.deviceMacId = deviceMacId;
    }

    @XmlElement(name="InstallCode")
    public String getInstallCode() {
        return installCode;
    }

    public void setInstallCode(String installCode) {
        this.installCode = installCode;
    }

    @XmlElement(name="LinkKey")
    public String getLinkKey() {
        return linkKey;
    }

    public void setLinkKey(String linkKey) {
        this.linkKey = linkKey;
    }

    @XmlElement(name="FWVersion")
    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    @XmlElement(name="HWVersion")
    public String getHardwareVersion() {
        return hardwareVersion;
    }

    public void setHardwareVersion(String hardwareVersion) {
        this.hardwareVersion = hardwareVersion;
    }

    @XmlElement(name="ImageType")
    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    @XmlElement(name="Manufacturer")
    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    @XmlElement(name="ModelId")
    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    @XmlElement(name="DateCode")
    public String getDateCode() {
        return dateCode;
    }

    public void setDateCode(String dateCode) {
        this.dateCode = dateCode;
    }

    @Override
    public String toString() {
        return "DeviceInfo{" +
                "deviceMacId='" + deviceMacId + '\'' +
                ", installCode='" + installCode + '\'' +
                ", linkKey='" + linkKey + '\'' +
                ", firmwareVersion='" + firmwareVersion + '\'' +
                ", hardwareVersion='" + hardwareVersion + '\'' +
                ", imageType='" + imageType + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", modelId='" + modelId + '\'' +
                ", dateCode='" + dateCode + '\'' +
                '}';
    }
}
