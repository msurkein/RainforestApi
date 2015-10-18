package com.rainforestautomation.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="ConnectionStatus")
public class ConnectionStatus {
    private String deviceMacId;
    private String meterMacId;
    private String status;
    private String description;
    private String extendedPanId;
    private Integer channel;
    private String shortAddress;
    private String linkStrength;

    @XmlElement(name="DeviceMacId")
    public String getDeviceMacId() {
        return deviceMacId;
    }

    public void setDeviceMacId(String deviceMacId) {
        this.deviceMacId = deviceMacId;
    }

    @XmlElement(name="MeterMacId")
    public String getMeterMacId() {
        return meterMacId;
    }

    public void setMeterMacId(String meterMacId) {
        this.meterMacId = meterMacId;
    }

    @XmlElement(name="Status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @XmlElement(name="Description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @XmlElement(name="ExtPanId")
    public String getExtendedPanId() {
        return extendedPanId;
    }

    public void setExtendedPanId(String extendedPanId) {
        this.extendedPanId = extendedPanId;
    }
    @XmlElement(name="Channel")
    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }
    @XmlElement(name="ShortAddr")
    public String getShortAddress() {
        return shortAddress;
    }

    public void setShortAddress(String shortAddress) {
        this.shortAddress = shortAddress;
    }
    @XmlElement(name="LinkStrength")
    public String getLinkStrength() {
        return linkStrength;
    }

    public void setLinkStrength(String linkStrength) {
        this.linkStrength = linkStrength;
    }

    @Override
    public String toString() {
        return "ConnectionStatus{" +
                "deviceMacId='" + deviceMacId + '\'' +
                ", meterMacId='" + meterMacId + '\'' +
                ", status='" + status + '\'' +
                ", description='" + description + '\'' +
                ", extendedPanId='" + extendedPanId + '\'' +
                ", channel=" + channel +
                ", shortAddress='" + shortAddress + '\'' +
                ", linkStrength='" + linkStrength + '\'' +
                '}';
    }
}
