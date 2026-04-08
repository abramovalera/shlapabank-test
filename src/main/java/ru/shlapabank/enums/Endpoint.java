package ru.shlapabank.enums;

public enum Endpoint {

    BASE_URL("http://155.212.170.64");



    private final String path;

    Endpoint(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}

