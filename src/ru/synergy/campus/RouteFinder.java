package ru.synergy.campus;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/** Поиск маршрута в ширину (BFS). */
public final class RouteFinder {

    public RouteResult findRoute(
            CampusGraph graph,
            String start,
            String destination,
            Set<String> blockedPoints
    ) {
        if (graph == null) {
            return RouteResult.failure("Граф кампуса не задан");
        }
        if (start == null || start.isBlank() || destination == null || destination.isBlank()) {
            return RouteResult.failure("Необходимо выбрать обе точки маршрута");
        }
        if (!graph.contains(start) || !graph.contains(destination)) {
            return RouteResult.failure("Одна из выбранных точек отсутствует на карте");
        }
        if (start.equals(destination)) {
            return RouteResult.failure("Начальная и конечная точки совпадают");
        }

        Set<String> blocked = blockedPoints == null
                ? Set.of()
                : new HashSet<>(blockedPoints);

        if (blocked.contains(start) || blocked.contains(destination)) {
            return RouteResult.failure("Начальная или конечная точка временно недоступна");
        }

        Queue<String> queue = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();
        Map<String, String> previous = new HashMap<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            String current = queue.remove();
            if (current.equals(destination)) {
                return RouteResult.success(restoreRoute(previous, start, destination));
            }

            for (String neighbour : graph.neighboursOf(current)) {
                if (blocked.contains(neighbour) || visited.contains(neighbour)) {
                    continue;
                }
                visited.add(neighbour);
                previous.put(neighbour, current);
                queue.add(neighbour);
            }
        }

        return RouteResult.failure("Маршрут между выбранными точками не найден");
    }

    private List<String> restoreRoute(
            Map<String, String> previous,
            String start,
            String destination
    ) {
        List<String> route = new ArrayList<>();
        String current = destination;

        while (current != null) {
            route.add(current);
            if (current.equals(start)) {
                break;
            }
            current = previous.get(current);
        }

        Collections.reverse(route);
        return route;
    }
}
