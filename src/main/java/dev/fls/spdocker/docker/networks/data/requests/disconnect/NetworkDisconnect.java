package dev.fls.spdocker.docker.networks.data.requests.disconnect;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class NetworkDisconnect {

    private final String Container;
    private boolean Force;
}
