package org.sopt.domain;

public enum Gender {
    MALE("남성"),
    FEMALE("여성");

    private String label;

    Gender(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
