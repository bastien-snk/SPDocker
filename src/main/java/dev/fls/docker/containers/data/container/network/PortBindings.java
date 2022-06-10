package dev.fls.docker.containers.data.container.network;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PortBindings {

    private String HostIp = "0.0.0.0";
    private String HostPort;

    public void setHostPort(int hostPort) {
        HostPort = String.valueOf(hostPort);
    }
}
