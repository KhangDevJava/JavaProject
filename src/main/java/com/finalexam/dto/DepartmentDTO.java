package com.finalexam.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
public class DepartmentDTO extends RepresentationModel<DepartmentDTO>{

    private int id;

    private String name;

    @Data
    @NoArgsConstructor
    public static class AccountDTO extends RepresentationModel<DepartmentDTO> {

        @JsonProperty("accountId")
        private int id;

        private String username;
    }
}
