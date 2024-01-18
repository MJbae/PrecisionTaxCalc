package com.o3.mj.usecase.dto;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScrapedResponse {
    private Data data;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data {
        @JsonProperty("jsonList")
        private JsonList jsonList;

        @Getter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class JsonList {
            @JsonProperty("급여")
            private List<Salary> salaries;
            @JsonProperty("산출세액")
            private String calculatedTaxAmount;
            @JsonProperty("소득공제")
            private List<IncomeDeduction> incomeDeductions;
        }
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Salary {
        @JsonProperty("총지급액")
        private String amount;
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class IncomeDeduction {
        @JsonAlias({"금액", "총납임금액"})
        private String amount;
        @JsonProperty("소득구분")
        private String deductionType;
    }
}
