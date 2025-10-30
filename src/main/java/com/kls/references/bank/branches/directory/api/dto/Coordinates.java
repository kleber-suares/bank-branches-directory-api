package com.kls.references.bank.branches.directory.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
@Getter
public class Coordinates {

    private Double xCoord;
    private Double yCoord;

}
