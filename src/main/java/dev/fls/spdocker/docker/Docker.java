package dev.fls.spdocker.docker;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.fls.spdocker.docker.containers.ContainerManager;
import dev.fls.spdocker.docker.networks.NetworkManager;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.net.URI;

@Getter
@Setter
public class Docker {

    public static final Gson DESERIALIZER = new GsonBuilder()
            .serializeNulls()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS'Z'")
            .create();

    public static final Gson SERIALIZER = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS'Z'")
            .create();


    private URI host = URI.create("http://localhost:2375");
    private ApiVersion version = ApiVersion.V1_41;

    @Getter(value = AccessLevel.PRIVATE)
    private final ContainerManager containerManager = new ContainerManager(this);

    @Getter(value = AccessLevel.PRIVATE)
    private final NetworkManager networkManager = new NetworkManager(this);

    public ContainerManager containers() {
        return containerManager;
    }

    public NetworkManager networks() {
        return networkManager;
    }


    public static class Builder {

        private final Docker docker = new Docker();

        public Builder withHost(String host) {
            docker.setHost(URI.create(host));
            return this;
        }

        public Builder withVersion(ApiVersion version) {
            docker.setVersion(version);
            return this;
        }

        public Docker build() {
            return docker;
        }
    }
}
