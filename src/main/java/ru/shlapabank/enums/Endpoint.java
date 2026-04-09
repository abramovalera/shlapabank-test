package ru.shlapabank.enums;

/**
 * Справочник API-эндпоинтов, используемых в тестах.
 */
public enum Endpoint {
    // Auth
    /** Регистрация пользователя. */
    REGISTER("/api/v1/auth/register"),
    /** Авторизация пользователя. */
    LOGIN("/api/v1/auth/login"),

    // Admin
    /** Список пользователей (админ). */
    ADMIN_USERS("/api/v1/admin/users"),
    /** Удаление пользователя по id (админ). */
    ADMIN_USER_DELETE("/api/v1/admin/users/{user_id}"),

    // Profile
    /** Получение и обновление профиля текущего пользователя. */
    PROFILE("/api/v1/profile"),

    // Accounts
    /** Работа со счетами пользователя. */
    ACCOUNTS("/api/v1/accounts"),
    /** Пополнение счета по id. */
    ACCOUNT_TOPUP("/api/v1/accounts/{account_id}/topup"),
    /** Закрытие счета по id. */
    ACCOUNT_CLOSE("/api/v1/accounts/{account_id}"),
    /** Установка приоритетных счетов. */
    ACCOUNTS_PRIMARY("/api/v1/accounts/primary"),

    // Transfers
    /** Переводы между счетами. */
    TRANSFERS("/api/v1/transfers"),
    /** Курсы валют для переводов/обмена. */
    TRANSFERS_RATES("/api/v1/transfers/rates"),
    /** Суточные лимиты переводов. */
    TRANSFERS_DAILY_USAGE("/api/v1/transfers/daily-usage"),

    // Transactions
    /** История операций пользователя. */
    TRANSACTIONS("/api/v1/transactions"),

    // Payments
    /** Список мобильных операторов для платежей. */
    PAYMENTS_MOBILE_OPERATORS("/api/v1/payments/mobile/operators"),
    /** Список поставщиков для платежей. */
    PAYMENTS_VENDOR_PROVIDERS("/api/v1/payments/vendor/providers"),

    // Service
    /** Healthcheck сервиса. */
    HEALTH("/health"),
    /** Просмотр OTP-кода для тестовых сценариев. */
    OTP_PREVIEW("/api/v1/helper/otp/preview");

    private final String path;

    Endpoint(String path) {
        this.path = path;
    }

    /**
     * Возвращает URL-путь эндпоинта.
     */
    public String path() {
        return path;
    }
}
