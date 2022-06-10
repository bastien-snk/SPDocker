package dev.fls.docker;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@RequiredArgsConstructor
public abstract class Manager {

    private final Docker docker;

    /**
     * This contains the container ressource route for each versions
     *
     * List<ApiVersion> -> contains the list of api versions associated to a route
     * String -> The route
     */
    private final Map<List<ApiVersion>, String> routes;

    protected String getRoute(ApiVersion apiVersion) {
        for(Map.Entry<List<ApiVersion>, String> entry : routes.entrySet()) {
            List<ApiVersion> versions = entry.getKey();
            if(!versions.contains(apiVersion)) continue;

            return entry.getValue();
        }
        return null;
    }
}