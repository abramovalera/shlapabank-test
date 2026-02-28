package enums;

/**
 * Перечень HTTP‑эндпоинтов Шляпа Банка.
 */
public enum Endpoint {

    REGISTER("/api/v1/auth/register");

    private final String path;

    Endpoint(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}

