package dev.fls.docker.containers.data.container.network;

import dev.fls.docker.containers.data.container.volumes.VolumeMount;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class HostConfig {

    private final Map<String, List<PortBindings>> PortBindings = new HashMap<>();
    private final List<VolumeMount> Mounts = new ArrayList();
    private final List<String> Binds = new ArrayList<>();
    private String NetworkMode;

    public static class Builder {

        private final HostConfig config = new HostConfig();

        public Builder withPort(Port containerPort, Port hostPort) {
            PortBindings binding = new PortBindings();
            binding.setHostPort(hostPort.port());
            config.getPortBindings().put(containerPort.get(), List.of(binding));
            return this;
        }

        public Builder withNetworkMode(String networkMode) {
            config.setNetworkMode(networkMode);
            return this;
        }

        public HostConfig build() {
            return config;
        }
    }
}
