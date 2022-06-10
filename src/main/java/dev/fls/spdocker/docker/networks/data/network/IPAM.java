package dev.fls.spdocker.docker.networks.data.network;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class IPAM {

    private String Driver = "default";

    @Getter
    @Setter
    public static class Config {

        private String Subnet, IPRange, Gateway;
    }

    private String IPv4Address, IPv6Address;
    private Map<String, String> Options = new HashMap<>();
    private final List<Config> Config = new ArrayList<>();

    public static class Builder {

        private final IPAM ipam = new IPAM();

        public Builder withIPv4(String ip) {
            ipam.setIPv4Address(ip);
            return this;
        }

        public Builder withIPv6(String ip) {
            ipam.setIPv6Address(ip);
            return this;
        }

        public Builder addConfig(Config config) {
            ipam.getConfig().add(config);
            return this;
        }

        public IPAM build() {
            return ipam;
        }
    }
}
