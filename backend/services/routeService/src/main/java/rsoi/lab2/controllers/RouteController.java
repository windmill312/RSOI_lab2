package rsoi.lab2.controllers;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rsoi.lab2.entity.Route;
import rsoi.lab2.model.RouteInfo;
import rsoi.lab2.services.RouteService;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@RestController
public class RouteController {

    @Autowired
    public RouteService routeService;
    Logger logger = Logger.getLogger(RouteController.class.getName());

    public RouteController(RouteService service) {
        this.routeService = service;
    }

    @GetMapping("/ping")
    public ResponseEntity<Object> ping() {
        logger.info("Get \"ping\" request.");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/routes")
    public ResponseEntity<List<RouteInfo>> getRoutes(@RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "size", defaultValue = "5") int size) {
        logger.info("Get \"routes\" request with params (page=" + page + ", size=" + size + ").");
        List<RouteInfo> list = routeService.listAll();
        if ((size * page) > list.size())
            return ResponseEntity.ok(list.subList((size * (page - 1)), (size * page) - ((size * page) - list.size())));
        else
            return ResponseEntity.ok(list.subList(size * (page - 1), size * page));
    }

    @GetMapping(value = "/route",
            params = "idRoute",
            produces = "application/json")
    public ResponseEntity<RouteInfo> getRoute(@RequestParam Integer idRoute) {
        logger.info("Get \"show\" request with param (idRoute=" + idRoute + ").");
        return ResponseEntity.ok(routeService.getRouteInfoById(idRoute));
    }

    @GetMapping(value = "/routes",
            params = "nmRoute",
            produces = "application/json")
    public ResponseEntity<List<RouteInfo>> getRoutes(@RequestParam String nmRoute, @RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "size", defaultValue = "5") int size) {
        logger.info("Get \"routes\" request with param (nmRoute=" + nmRoute + ", page=" + page + ", size=" + size + ").");
        List<RouteInfo> list = routeService.listAllByNmRoute(nmRoute);
        if ((size * page) > list.size())
            return ResponseEntity.ok(list.subList((size * (page - 1)), (size * page) - ((size * page) - list.size())));
        else
            return ResponseEntity.ok(list.subList(size * (page - 1), size * page));
    }

    @PutMapping("/route")
    public ResponseEntity<String> add(@RequestBody RouteInfo route) {
        try {
            logger.info("Get PUT request (add) with params (routeName=" + route.getRouteName() + ", idRoute=" + route.getIdRoute() + ").");
            Route newRoute = new Route(route.getRouteName());
            if (route.getIdRoute() != 0)
                newRoute.setIdRoute(route.getIdRoute());
            newRoute.setUid(UUID.randomUUID());
            routeService.saveOrUpdate(newRoute);
            return ResponseEntity.ok(new Gson().toJson(newRoute.getUid()));
        } catch (Exception e) {
            logger.info(e.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/route")
    public ResponseEntity edit(@RequestBody Route route) {
        try {
            logger.info("Get PATCH request (edit) with param (idRoute=" + route.getIdRoute() + ", nmRoute=" + route.getNmRoute() + ").");
            Route newRoute = routeService.getRouteById(route.getIdRoute());
            newRoute.setNmRoute(route.getNmRoute());
            routeService.saveOrUpdate(newRoute);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.info(e.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/route")
    public ResponseEntity delete(@RequestBody int idRoute) {
        try {
            logger.info("Get DELETE request (delete) with param (idRoute=" + idRoute + ").");
            routeService.delete(idRoute);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.info(e.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
