package ru.synergy.campus;

import java.util.List;

/** Результат поиска: найденный путь или текст ошибки. */
public final class RouteResult {
    private final boolean successful;
    private final List<String> route;
    private final String message;

    private RouteResult(boolean successful, List<String> route, String message) {
        this.successful = successful;
        this.route = List.copyOf(route);
        this.message = message;
    }

    public static RouteResult success(List<String> route) {
        return new RouteResult(true, route, "Маршрут найден");
    }

    public static RouteResult failure(String message) {
        return new RouteResult(false, List.of(), message);
    }

    public boolean isSuccessful() {
        return successful;
    }

    public List<String> getRoute() {
        return route;
    }

    public String getMessage() {
        return message;
    }
}
