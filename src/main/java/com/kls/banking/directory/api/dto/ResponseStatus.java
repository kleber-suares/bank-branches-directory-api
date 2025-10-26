package com.kls.banking.directory.api.dto;

public enum ResponseStatus {

    SUCCESS("sucess"),
    ERROR("error");

    private String status;

    ResponseStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

}
