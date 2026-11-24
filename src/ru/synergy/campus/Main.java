package ru.synergy.campus;

import java.util.Set;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        CampusGraph graph = CampusGraph.createNagatinskayaDemo();
        RouteFinder finder = new RouteFinder();

        printResult(
                "Обычный маршрут",
                finder.findRoute(graph, "Вход", "Кабинет 830", Set.of())
        );

        printResult(
                "Маршрут при закрытом лифте",
                finder.findRoute(
                        graph,
                        "Вход",
                        "Кабинет 830",
                        Set.of("Лифт 1 этажа", "Лифт 7 этажа", "Лифт 8 этажа")
                )
        );
    }

    private static void printResult(String title, RouteResult result) {
        System.out.println("\n" + title);
        System.out.println(result.getMessage());

        if (result.isSuccessful()) {
            for (int step = 0; step < result.getRoute().size(); step++) {
                System.out.printf("%d. %s%n", step + 1, result.getRoute().get(step));
            }
        }
    }
}
