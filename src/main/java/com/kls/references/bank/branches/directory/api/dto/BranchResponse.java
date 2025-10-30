package com.kls.references.bank.branches.directory.api.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class BranchResponse implements Serializable {

    private String branch;
    private Double distance;

}
