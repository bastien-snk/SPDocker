package dev.fls.spdocker.docker.containers.data.container.network;

public record Port(int port, String protocol) {

    public String get() {
        return port + "/" + protocol;
    }

}