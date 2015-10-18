package com.rainforestautomation.model.utility;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class HexLongAdapter extends XmlAdapter<String, Long> {
    @Override
    public Long unmarshal(String v) throws Exception {
        try {
            Long i = Long.parseLong(v.replace("0x", ""), 16);
            return i;
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String marshal(Long v) throws Exception {
        return Long.toHexString(v);
    }
}
