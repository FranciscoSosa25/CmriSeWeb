package com.cmrise.dicom.attribute;

import java.util.List;

public class DicomAttribute {
    private String name;
    private List<String> values;

    public DicomAttribute(String name, List<String> values) {
        this.name = name;
        this.values = values;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public String plainStringValues() {
        String result = "";
        for (String it : values) {
            if (result.isEmpty()) {
                result += it;
            } else {
                result += "," + it;
            }
        }
        return result;
    }
}
