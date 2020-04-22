package cn.org.rookie.mapper.entity;

import cn.org.rookie.mapper.annotation.Association;

public class AssociationInfo {

    private final String target;
    private final String association;

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
