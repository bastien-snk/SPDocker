package dev.fls.spdocker.docker.networks.data.requests.connect;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class NetworkConnect {

    private final String Container;
    private EndpointConfig EndpointConfig = new EndpointConfig();

}