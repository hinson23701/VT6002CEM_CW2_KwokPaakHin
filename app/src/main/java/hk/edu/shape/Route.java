package hk.edu.shape;

public class Route {
    private String routeNumber;
    private String origin;
    private String destination;

    public Route(String routeNumber, String origin, String destination) {
        this.routeNumber = routeNumber;
        this.origin = origin;
        this.destination = destination;
    }

    public String getRouteNumber() {
        return routeNumber;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }
}
