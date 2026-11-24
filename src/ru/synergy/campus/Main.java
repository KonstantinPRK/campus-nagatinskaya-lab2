package ru.synergy.campus;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        CampusGraph graph = CampusGraph.createNagatinskayaDemo();
        RouteFinder finder = new RouteFinder();
        RouteResult result = finder.findRoute(graph, "Вход", "Кабинет 830");

        System.out.println(result.getMessage());
        if (result.isSuccessful()) {
            for (int step = 0; step < result.getRoute().size(); step++) {
                System.out.printf("%d. %s%n", step + 1, result.getRoute().get(step));
            }
        }
    }
}
