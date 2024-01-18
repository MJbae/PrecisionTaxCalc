package com.o3.mj.domain.customer;

import java.util.HashSet;
import java.util.Set;

public class SignUpWhiteList {
    private final Set<ResidentId> whiteList = new HashSet<>() {{
        add(new ResidentId("860824-1655068"));
        add(new ResidentId("921108-1582816"));
        add(new ResidentId("880601-2455116"));
        add(new ResidentId("910411-1656116"));
        add(new ResidentId("820326-2715702"));
    }};

    public boolean isAllowedToSignup(ResidentId residentId) {
        return whiteList.contains(residentId);
    }
}
