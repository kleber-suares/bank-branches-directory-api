package com.kls.references.bank.branches.directory.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SaveBranchRequest {

    private String branchName;
    @JsonProperty("xCoord")
    private Double xCoord;
    @JsonProperty("yCoord")
    private Double yCoord;

}
