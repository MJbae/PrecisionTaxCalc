package com.o3.mj.domain;

public enum DeductionType {
    INSURANCE("보험료"),
    EDUCATION("교육비"),
    DONATION("기부금"),
    MEDICAL("의료비"),
    RETIREMENT_PENSION("퇴직연금");

    private final String description;

    DeductionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static DeductionType fromDescription(String description) {
        for (DeductionType type : DeductionType.values()) {
            if (type.getDescription().equals(description)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No constant with description " + description + " found");
    }
}
