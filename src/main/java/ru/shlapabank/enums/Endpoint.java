package ru.shlapabank.enums;

public enum Endpoint {

    REGISTER("/api/v1/auth/register"),
    BASE_URL("http://155.212.170.64"),
    LOGIN("/api/v1/auth/login"),
    ACCOUNTS("/api/v1/accounts");

    private final String path;

    Endpoint(String path) {
        this.path = path;
    }

    public String path() {
        return path;
    }
}

