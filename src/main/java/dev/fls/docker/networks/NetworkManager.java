package dev.fls.docker.networks;

import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;
import dev.fls.docker.ApiVersion;
import dev.fls.docker.Docker;
import dev.fls.docker.Manager;
import dev.fls.docker.networks.data.network.Network;
import dev.fls.docker.networks.data.requests.connect.NetworkConnect;
import dev.fls.docker.networks.data.requests.disconnect.NetworkDisconnect;
import dev.fls.utils.HttpRequest;
import dev.fls.utils.HttpResponseCode;

import java.net.URI;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class NetworkManager extends Manager {

    public NetworkManager(Docker docker) {
        super(
                docker,
                ImmutableMap.<List<ApiVersion>, String>builder()
                        .put(ApiVersion.get(ApiVersion.V1_21, ApiVersion.V1_41), "/networks")
                        .build()
        );
    }

    public List<Network> list() {
        String ressourceRoute = getRoute(getDocker().getVersion());
        if(ressourceRoute == null) return null;

        List<Network> networks = new ArrayList<>();

        switch (getDocker().getVersion()) {
            case V1_41 -> {
                URI route = URI.create(getDocker().getHost() + ressourceRoute);
                HttpResponse response = HttpRequest.request(HttpRequest.RequestType.GET, route, null);

                HttpResponseCode code = HttpResponseCode.get(response.statusCode());
                boolean success = code.equals(HttpResponseCode.SUCCESS);
                if(!success) return null;

                String json = (String) response.body();
                System.out.println("RECEIVED " + json);
                networks = Docker.DESERIALIZER.fromJson(json, new TypeToken<List<Network>>(){}.getType());
            }

            default -> {
                return null;
            }
        }

        System.out.println(Docker.SERIALIZER.toJson(networks));
        return networks;
    }

    public Network get(String network) {
        String ressourceRoute = getRoute(getDocker().getVersion());
        if(ressourceRoute == null) return null;


        switch (getDocker().getVersion()) {
            case V1_41 -> {
                URI route = URI.create(getDocker().getHost() + ressourceRoute + "/" + network);

                HttpResponse<String> response = HttpRequest.request(HttpRequest.RequestType.GET, route, null);
                if(response == null) return null;
                HttpResponseCode code = HttpResponseCode.get(response.statusCode());

                boolean success = code.equals(HttpResponseCode.SUCCESS);
                if(!success) return null;

                String json = (String) response.body();
                return Docker.DESERIALIZER.fromJson(json, Network.class);
            }

            default -> {
                return null;
            }
        }
    }

    public List<Network> get(List<String> networkNames) {
        List<Network> networks = new ArrayList<>();

        for(String networkName : networkNames) {
            Network network = get(networkName);
            if(network == null) continue;
            networks.add(network);
        }

        return networks;
    }

    public boolean create(Network network) {
        String ressourceRoute = getRoute(getDocker().getVersion());
        if(ressourceRoute == null) return false;

        switch (getDocker().getVersion()) {
            case V1_41 -> {
                URI route = URI.create(getDocker().getHost() + ressourceRoute + "/create");

                HttpResponse<String> response = HttpRequest.request(HttpRequest.RequestType.POST, route, Docker.SERIALIZER.toJson(network));
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

    public boolean remove(Network network) {
        return remove(network.getName());
    }

    public boolean remove(String network) {
        String ressourceRoute = getRoute(getDocker().getVersion());
        if (ressourceRoute == null) return false;

        switch (getDocker().getVersion()) {
            case V1_41 -> {
                URI route = URI.create(getDocker().getHost() + ressourceRoute + "/" + network);

                HttpResponse<String> response = HttpRequest.request(HttpRequest.RequestType.DELETE, route, null);
                if (response == null) return false;
                HttpResponseCode code = HttpResponseCode.get(response.statusCode());
                System.out.println(response.body());
                return code.equals(HttpResponseCode.SUCCESS);
            }

            default -> {
                return false;
            }
        }
    }

    /**
     * Connect a container to a network
     * @return
     */
    public boolean connect(Network network, NetworkConnect data) {
        String ressourceRoute = getRoute(getDocker().getVersion());
        if(ressourceRoute == null) return false;

        switch (getDocker().getVersion()) {
            case V1_41 -> {
                URI route = URI.create(getDocker().getHost() + ressourceRoute + "/" + network.getName() + "/connect");

                String body = Docker.SERIALIZER.toJson(data);
                HttpResponse<String> response = HttpRequest.request(HttpRequest.RequestType.POST, route, body);
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

    /**
     *
     * @param network
     * @param data
     * @param reconnect Tells if Disconnect & connect if container is already in the specified network
     * @return
     */
    public boolean connect(Network network, NetworkConnect data, boolean reconnect) {
        if(reconnect) disconnect(network, new NetworkDisconnect(data.getContainer()));
        return connect(network, data);
    }

    /**
     * Disconnect a container from a network
     * @return
     */
    public boolean disconnect(Network network, NetworkDisconnect data) {
        String ressourceRoute = getRoute(getDocker().getVersion());
        if(ressourceRoute == null) return false;

        switch (getDocker().getVersion()) {
            case V1_41 -> {
                URI route = URI.create(getDocker().getHost() + ressourceRoute + "/" + network.getName() + "/disconnect");
                System.out.println("DEBUG: " + Docker.SERIALIZER.toJson(data));
                HttpResponse<String> response = HttpRequest.request(HttpRequest.RequestType.POST, route, Docker.SERIALIZER.toJson(data));
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
Delete unused networks
 */