package ru.synergy.campus;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/** Невзвешенный неориентированный граф основных точек кампуса. */
public final class CampusGraph {
    private final Map<String, Set<String>> adjacency = new LinkedHashMap<>();

    public void addPoint(String point) {
        adjacency.computeIfAbsent(point, ignored -> new LinkedHashSet<>());
    }

    public void connect(String firstPoint, String secondPoint) {
        addPoint(firstPoint);
        addPoint(secondPoint);
        adjacency.get(firstPoint).add(secondPoint);
        adjacency.get(secondPoint).add(firstPoint);
    }

    public boolean contains(String point) {
        return adjacency.containsKey(point);
    }

    public Set<String> neighboursOf(String point) {
        return adjacency.containsKey(point)
                ? Collections.unmodifiableSet(adjacency.get(point))
                : Set.of();
    }

    public Set<String> points() {
        return Collections.unmodifiableSet(adjacency.keySet());
    }

    public static CampusGraph createNagatinskayaDemo() {
        CampusGraph graph = new CampusGraph();

        graph.connect("Вход", "Холл 1 этажа");
        graph.connect("Холл 1 этажа", "Столовая");
        graph.connect("Холл 1 этажа", "Лифт 1 этажа");
        graph.connect("Холл 1 этажа", "Лестница 1 этажа");

        graph.connect("Лифт 1 этажа", "Лифт 7 этажа");
        graph.connect("Лифт 1 этажа", "Лифт 8 этажа");
        graph.connect("Лифт 7 этажа", "Коридор 7 этажа");
        graph.connect("Лифт 8 этажа", "Коридор 8 этажа");

        for (int floor = 1; floor < 8; floor++) {
            graph.connect(
                    "Лестница " + floor + " этажа",
                    "Лестница " + (floor + 1) + " этажа"
            );
        }

        graph.connect("Лестница 7 этажа", "Коридор 7 этажа");
        graph.connect("Лестница 8 этажа", "Коридор 8 этажа");
        graph.connect("Коридор 7 этажа", "Аудитория 701");
        graph.connect("Коридор 8 этажа", "Аудитория 801");
        graph.connect("Коридор 8 этажа", "Кабинет 830");

        return graph;
    }
}
