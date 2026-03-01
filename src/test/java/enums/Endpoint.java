package enums;


public enum Endpoint {

    BASE_URL("http://localhost:8001"),
    REGISTER("/api/v1/auth/register"),
    LOGIN("/api/v1/auth/login");

    private final String path;

    Endpoint(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}

