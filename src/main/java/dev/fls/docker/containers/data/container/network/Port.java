package dev.fls.docker.containers.data.container.network;

public record Port(int port, String protocol) {

    public String get() {
        return port + "/" + protocol;
    }

}