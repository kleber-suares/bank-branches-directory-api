package com.kls.references.bank.branches.directory.api.dto;

import lombok.Data;

@Data
public class SaveBranchResponse<T> {

    private String status;
    private String message;

    private SaveBranchResponse() {}

    private SaveBranchResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public static SaveBranchResponse success(String message) {
        return new SaveBranchResponse<>(ResponseStatus.SUCCESS.getStatus(), message);
    }

    public static <T> SaveBranchResponse<T> error(String message) {
        return new SaveBranchResponse<>(ResponseStatus.ERROR.getStatus(), message);
    }

}
