package com.surkein.raven.model;

import com.surkein.raven.model.utility.HexLongAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.math.MathContext;

@XmlRootElement(name="InstantaneousDemand")
public class InstantaneousDemand {
    private String deviceMacId;
    private String meterMacId;
    private Long timeStamp;
    private Long demand;
    private Long multiplier;
    private Long divisor;
    private Long digitsRight;
    private Long digitsLeft;
    private String suppressLeadingZero;

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

    @XmlElement(name="TimeStamp")
    @XmlJavaTypeAdapter(HexLongAdapter.class)
    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    @XmlElement(name="Demand")
    @XmlJavaTypeAdapter(HexLongAdapter.class)
    public Long getDemand() {
        return demand;
    }

    public void setDemand(Long demand) {
        this.demand = demand;
    }

    @XmlElement(name="Multiplier")
    @XmlJavaTypeAdapter(HexLongAdapter.class)
    public Long getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(Long multiplier) {
        this.multiplier = multiplier;
    }

    @XmlElement(name="Divisor")
    @XmlJavaTypeAdapter(HexLongAdapter.class)
    public Long getDivisor() {
        return divisor;
    }

    public void setDivisor(Long divisor) {
        this.divisor = divisor;
    }

    @XmlElement(name="DigitsRight")
    @XmlJavaTypeAdapter(HexLongAdapter.class)
    public Long getDigitsRight() {
        return digitsRight;
    }

    public void setDigitsRight(Long digitsRight) {
        this.digitsRight = digitsRight;
    }

    @XmlElement(name="DigitsLeft")
    @XmlJavaTypeAdapter(HexLongAdapter.class)
    public Long getDigitsLeft() {
        return digitsLeft;
    }

    public void setDigitsLeft(Long digitsLeft) {
        this.digitsLeft = digitsLeft;
    }

    @XmlElement(name="SuppressLeadingZero")
    public String getSuppressLeadingZero() {
        return suppressLeadingZero;
    }

    public void setSuppressLeadingZero(String suppressLeadingZero) {
        this.suppressLeadingZero = suppressLeadingZero;
    }

    @XmlTransient
    public BigDecimal getDemandAsDecimal() {
        Long demand = getDemand();
        Long multiplier = getMultiplier();
        Long divisor = getDivisor();
        BigDecimal intermediateValue = BigDecimal.valueOf(demand);
        if(multiplier != null) {
            intermediateValue = intermediateValue.multiply(BigDecimal.valueOf(multiplier), MathContext.DECIMAL128);
        }
        if(divisor != null && divisor != 0) {
            intermediateValue = intermediateValue.divide(BigDecimal.valueOf(divisor), MathContext.DECIMAL128);
        }
        return intermediateValue;
    }
    @Override
    public String toString() {
        return "InstantaneousDemand{" +
                "deviceMacId='" + deviceMacId + '\'' +
                ", meterMacId='" + meterMacId + '\'' +
                ", timeStamp=" + timeStamp +
                ", demand=" + demand +
                ", multiplier=" + multiplier +
                ", divisor=" + divisor +
                ", digitsRight=" + digitsRight +
                ", digitsLeft=" + digitsLeft +
                ", suppressLeadingZero='" + suppressLeadingZero + '\'' +
                '}';
    }
}
