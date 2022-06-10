package dev.fls.docker.networks.data.network;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Network {


    private String Name, Id, Scope, Driver;
    private Date Created;
    private boolean EnableIPv6;
    private IPAM IPAM = new IPAM();

    private boolean Internal, Attachable, Ingress, CheckDuplicate;

    private Map<String, Container> Containers = new HashMap<>();
    private Map<String, String> Options = new HashMap<>();
    private Map<String, String> Labels = new HashMap<>();

    @Getter
    @Setter
    static class Container {

        private String Name, EndpointID, MacAddress, IPv4Address, IPv6Address;
    }

    public static class Builder {

        private final Network network = new Network();

        public Builder withName(String name) {
            network.setName(name);
            return this;
        }

        public Builder checkDuplicate(boolean check) {
            network.setCheckDuplicate(check);
            return this;
        }

        public Builder withDriver(NetworkDriver driver) {
            network.setDriver(driver.getId());
            return this;
        }

        public Builder enableIPv6(boolean enable) {
            network.setEnableIPv6(enable);
            return this;
        }

        public Builder withIPAM(dev.fls.docker.networks.data.network.IPAM ipam) {
            network.setIPAM(ipam);
            return this;
        }

        public Builder isInternal(boolean internal) {
            network.setInternal(internal);
            return this;
        }

        public Builder isAttachable(boolean attachable) {
            network.setAttachable(attachable);
            return this;
        }

        public Builder withOption(String key, String value) {
            network.getOptions().put(key, value);
            return this;
        }

        public Builder withLabel(String key, String value) {
            network.getLabels().put(key, value);
            return this;
        }

        public Network build() {
            return network;
        }
    }

}

