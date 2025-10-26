package com.kls.banking.directory.api.dto;

import lombok.Data;

@Data
public class SaveBranchRequest {

    private String branchName;
    private Double posX;
    private Double posY;

}
