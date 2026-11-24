package ru.synergy.campus;

import java.util.List;
import java.util.Set;

public final class RouteFinderTest {
    private RouteFinderTest() {
    }

    public static void main(String[] args) {
        CampusGraph graph = CampusGraph.createNagatinskayaDemo();
        RouteFinder finder = new RouteFinder();

        RouteResult elevatorRoute = finder.findRoute(
                graph, "Вход", "Кабинет 830", Set.of()
        );
        check(elevatorRoute.isSuccessful(), "Обычный маршрут должен быть найден");
        check(
                elevatorRoute.getRoute().contains("Лифт 8 этажа"),
                "Обычный маршрут должен проходить через лифт"
        );

        Set<String> closedElevator = Set.of(
                "Лифт 1 этажа", "Лифт 7 этажа", "Лифт 8 этажа"
        );
        RouteResult stairsRoute = finder.findRoute(
                graph, "Вход", "Кабинет 830", closedElevator
        );
        check(stairsRoute.isSuccessful(), "При закрытом лифте должен быть найден обход");
        check(
                stairsRoute.getRoute().contains("Лестница 8 этажа"),
                "Обходной маршрут должен проходить через лестницу"
        );
        check(
                stairsRoute.getRoute().stream().noneMatch(closedElevator::contains),
                "Маршрут не должен содержать закрытые точки"
        );

        RouteResult samePoint = finder.findRoute(
                graph, "Вход", "Вход", Set.of()
        );
        check(!samePoint.isSuccessful(), "Совпадающие точки должны вызвать ошибку");

        RouteResult unknownPoint = finder.findRoute(
                graph, "Вход", "Неизвестная аудитория", Set.of()
        );
        check(!unknownPoint.isSuccessful(), "Неизвестная точка должна вызвать ошибку");

        RouteResult noRoute = finder.findRoute(
                graph,
                "Вход",
                "Кабинет 830",
                Set.of("Лифт 1 этажа", "Лестница 1 этажа")
        );
        check(!noRoute.isSuccessful(), "При закрытых переходах маршрут не должен находиться");

        check(
                elevatorRoute.getRoute().equals(List.of(
                        "Вход",
                        "Холл 1 этажа",
                        "Лифт 1 этажа",
                        "Лифт 8 этажа",
                        "Коридор 8 этажа",
                        "Кабинет 830"
                )),
                "BFS должен выбрать кратчайший маршрут по числу переходов"
        );

        System.out.println("Все проверки успешно выполнены: 5 из 5");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
