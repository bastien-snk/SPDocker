package dev.fls.spdocker.docker.containers;

import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;
import dev.fls.spdocker.docker.ApiVersion;
import dev.fls.spdocker.docker.Docker;
import dev.fls.spdocker.docker.Manager;
import dev.fls.spdocker.docker.containers.data.container.Container;
import dev.fls.spdocker.docker.containers.data.requests.ContainerRemove;
import dev.fls.spdocker.exceptions.UnsupportedApiVersionException;
import dev.fls.spdocker.utils.HttpRequest;
import dev.fls.spdocker.utils.HttpResponseCode;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class ContainerManager extends Manager {

    public ContainerManager(Docker docker) {
        super(
                docker,
                ImmutableMap.<List<ApiVersion>, String>builder()
                        .put(ApiVersion.get(ApiVersion.V1_18, ApiVersion.V1_41), "/containers")
                        .build()
        );
    }

    public List<Container> list() {
        String ressourceRoute = getRoute(getDocker().getVersion());
        if(ressourceRoute == null) return null;

        List<Container> containers = new ArrayList<>();

        switch (getDocker().getVersion()) {
            case V1_41 -> {
                URI route = URI.create(getDocker().getHost() + ressourceRoute + "/json");
                HttpResponse response = HttpRequest.request(HttpRequest.RequestType.GET, route, null);

                HttpResponseCode code = HttpResponseCode.get(response.statusCode());
                boolean success = code.equals(HttpResponseCode.SUCCESS);
                if(!success) return null;

                String json = (String) response.body();
                containers = Docker.DESERIALIZER.fromJson(json, new TypeToken<List<Container>>(){}.getType());
            }

            default -> {
                return null;
            }
        }

        return containers;
    }

    private final ContainerCreator containerCreator = new ContainerCreator(this);

    public ContainerCreator create() {
        return containerCreator;
    }

    @RequiredArgsConstructor
    public class ContainerCreator {

        private final ContainerManager containerManager;

        public Container withImage(String containerName, String image) throws Exception {
            String json = Docker.SERIALIZER.toJson(new Container.Builder().withImage(image).build());
            return withData(containerName, json);
        }

        public Container withData(String containerName, String json) throws Exception {
            String ressourceRoute = getRoute(getDocker().getVersion());
            if(ressourceRoute == null) throw new UnsupportedApiVersionException();


            switch (getDocker().getVersion()) {
                case V1_41 -> {
                    URI route = URI.create(getDocker().getHost() + ressourceRoute + "/create?name=" + containerName);

                    HttpResponse<String> response = HttpRequest.request(HttpRequest.RequestType.POST, route, json);
                    if(response == null) return null;
                    HttpResponseCode code = HttpResponseCode.get(response.statusCode());
                    System.out.println(response.body());
                    boolean success = code.equals(HttpResponseCode.SUCCESS);
                    if(!success) return null;
                    return containerManager.get(containerName);
                }

                default -> throw new UnsupportedApiVersionException();
            }
        }
    }

    /**
     *
     * @param container Container name or ID
     * @return
     */
    public Container get(String container) {
        String ressourceRoute = getRoute(getDocker().getVersion());
        if(ressourceRoute == null) return null;


        switch (getDocker().getVersion()) {
            case V1_41 -> {
                URI route = URI.create(getDocker().getHost() + ressourceRoute + "/" + container + "/json");

                HttpResponse<String> response = HttpRequest.request(HttpRequest.RequestType.GET, route, null);
                if(response == null) return null;
                HttpResponseCode code = HttpResponseCode.get(response.statusCode());

                boolean success = code.equals(HttpResponseCode.SUCCESS);
                if(!success) return null;

                String json = (String) response.body();
                return Docker.DESERIALIZER.fromJson(json, Container.class);
            }

            default -> {
                return null;
            }
        }
    }

    public boolean start(String containerName) {
        String ressourceRoute = getRoute(getDocker().getVersion());
        if(ressourceRoute == null) return false;

        switch (getDocker().getVersion()) {
            case V1_41 -> {
                URI route = URI.create(getDocker().getHost() + ressourceRoute + "/" + containerName + "/start");

                HttpResponse<String> response = HttpRequest.request(HttpRequest.RequestType.POST, route, null);
                if(response == null) return false;
                HttpResponseCode code = HttpResponseCode.get(response.statusCode());
                System.out.println(response.body());
                return code.equals(HttpResponseCode.SUCCESS);
            }

            default -> {
                return false;
            }
        }
    }

    public boolean stop(String container) {
        String ressourceRoute = getRoute(getDocker().getVersion());
        if(ressourceRoute == null) return false;

        switch (getDocker().getVersion()) {
            case V1_41 -> {
                URI route = URI.create(getDocker().getHost() + ressourceRoute + "/" + container + "/stop");

                HttpResponse<String> response = HttpRequest.request(HttpRequest.RequestType.POST, route, null);
                if(response == null) return false;
                HttpResponseCode code = HttpResponseCode.get(response.statusCode());
                System.out.println(response.body());
                return code.equals(HttpResponseCode.SUCCESS);
            }

            default -> {
                return false;
            }
        }
    }

    public boolean restart(Container container) {
        String ressourceRoute = getRoute(getDocker().getVersion());
        if(ressourceRoute == null) return false;

        switch (getDocker().getVersion()) {
            case V1_41 -> {
                URI route = URI.create(getDocker().getHost() + ressourceRoute + "/" + container.getConfig().getHostname() + "/restart");

                HttpResponse<String> response = HttpRequest.request(HttpRequest.RequestType.POST, route, null);
                if(response == null) return false;
                HttpResponseCode code = HttpResponseCode.get(response.statusCode());
                System.out.println(response.body());
                return code.equals(HttpResponseCode.SUCCESS);
            }

            default -> {
                return false;
            }
        }
    }

    public boolean kill(Container container) {
        String ressourceRoute = getRoute(getDocker().getVersion());
        if(ressourceRoute == null) return false;

        switch (getDocker().getVersion()) {
            case V1_41 -> {
                URI route = URI.create(getDocker().getHost() + ressourceRoute + "/" + container.getConfig().getHostname() + "/kill");

                HttpResponse<String> response = HttpRequest.request(HttpRequest.RequestType.POST, route, null);
                if(response == null) return false;
                HttpResponseCode code = HttpResponseCode.get(response.statusCode());
                System.out.println(response.body());
                return code.equals(HttpResponseCode.SUCCESS);
            }

            default -> {
                return false;
            }
        }
    }

    public boolean rename(Container container, String name) {
        String ressourceRoute = getRoute(getDocker().getVersion());
        if(ressourceRoute == null) return false;

        switch (getDocker().getVersion()) {
            case V1_41 -> {
                URI route = URI.create(getDocker().getHost() + ressourceRoute + "/" + container.getConfig().getHostname() + "/rename?name=" + name);

                HttpResponse<String> response = HttpRequest.request(HttpRequest.RequestType.POST, route, null);
                if(response == null) return false;
                HttpResponseCode code = HttpResponseCode.get(response.statusCode());
                System.out.println(response.body());
                return code.equals(HttpResponseCode.SUCCESS);
            }

            default -> {
                return false;
            }
        }
    }

    public boolean remove(String container, ContainerRemove data) {
        String ressourceRoute = getRoute(getDocker().getVersion());
        if(ressourceRoute == null) return false;

        switch (getDocker().getVersion()) {
            case V1_41 -> {
                URI route = URI.create(getDocker().getHost() + ressourceRoute + "/" + container);

                HttpResponse<String> response = HttpRequest.request(HttpRequest.RequestType.DELETE, route, Docker.SERIALIZER.toJson(data));
                if(response == null) return false;
                HttpResponseCode code = HttpResponseCode.get(response.statusCode());
                System.out.println(response.body());
                return code.equals(HttpResponseCode.SUCCESS);
            }

            default -> {
                return false;
            }
        }
    }
}

/*
List processes running inside a container
Get container logs
Get changes on a container’s filesystem
Export a container
Get container stats based on resource usage
Resize a container TTY
Update a container
Pause a container
Unpause a container
Attach to a container
Attach to a container via a websocket
Wait for a container
Remove a container
Get information about files in a container
Get an archive of a filesystem resource in a container
Extract an archive of files or folders to a directory in a container
Delete stopped containers
 */