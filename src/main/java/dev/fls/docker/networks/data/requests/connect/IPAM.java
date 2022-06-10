package dev.fls.docker.networks.data.requests.connect;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class IPAM {

    private String IPv4Address, IPv6Address;
    private final List<String> LinkLocalIPs = new ArrayList<>();
}