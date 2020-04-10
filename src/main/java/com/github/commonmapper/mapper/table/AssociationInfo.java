package com.github.commonmapper.mapper.table;

import com.github.commonmapper.mapper.annotation.Association;

public class AssociationInfo {

    private String target;
    private String association;

    public AssociationInfo(Association association) {
        this.target = association.target();
        this.association = association.association();
    }

    public String getTarget() {
        return target;
    }

    public String getAssociation() {
        return association;
    }

}
