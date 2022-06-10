package dev.fls.docker.networks.data.requests.connect;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class EndpointConfig {

    private IPAM IPAMConfig = new IPAM();
    private final List<String> Aliases = new ArrayList<>(), Links = new ArrayList<>();
    private String NetworkID, EndpointID, Gateway, IPAddress;
    private int IPPrefixLen;
    private String IPv6Gateway, GlobalIPv6Address;
    private int GlobalIPv6PrefixLen;
    private String MacAddress;

    public static class Builder {

        private final EndpointConfig config = new EndpointConfig();

        public Builder withIPAM(IPAM ipam) {
            config.setIPAMConfig(ipam);
            return this;
        }

        public Builder addAlias(String alias) {
            config.getAliases().add(alias);
            return this;
        }

        public Builder addLink(String link) {
            config.getLinks().add(link);
            return this;
        }

        public Builder withNetworkID(String id) {
            config.setNetworkID(id);
            return this;
        }

        public Builder withEndpointID(String id) {
            config.setEndpointID(id);
            return this;
        }

        public Builder withGateway(String gateway) {
            config.setGateway(gateway);
            return this;
        }

        public Builder withIP(String ip) {
            config.setIPAddress(ip);
            return this;
        }

        public Builder withIPPrefixLen(int length) {
            config.setIPPrefixLen(length);
            return this;
        }

        public Builder withIPv6Gateway(String gateway) {
            config.setIPv6Gateway(gateway);
            return this;
        }

        public Builder withGlobalIPv6(String ip) {
            config.setGlobalIPv6Address(ip);
            return this;
        }

        public Builder withIPv6PrefixLen(int length) {
            config.setGlobalIPv6PrefixLen(length);
            return this;
        }

        public Builder withMacAddress(String mac) {
            config.setMacAddress(mac);
            return this;
        }

        public EndpointConfig build() {
            return config;
        }
    }
}
