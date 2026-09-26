package com.shubham.portfolio.service;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource) {
        super(resource + " was not found.");
    }
}
