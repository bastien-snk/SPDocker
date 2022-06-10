package dev.fls.docker.containers.data.container;

import dev.fls.docker.containers.data.container.network.HostConfig;
import dev.fls.docker.containers.data.container.volumes.MountType;
import dev.fls.docker.containers.data.container.volumes.VolumeMount;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Container {

    private String Image, Id;
    private boolean AutoRemove;
    private String Hostname, Domainname, User;
    private List<String> Env = new ArrayList<>();
    private HostConfig HostConfig = new HostConfig();
    private final Config Config = new Config();
    private boolean Tty, OpenStdin;

    /**
     * String -> volume
     * String -> Data
     */
    private final Map<String, String> Volumes = new HashMap<>();

    private final List<String> Args = new ArrayList<>();


    public static class Builder {
        private final Container container = new Container();

        public Builder withName(String name) {
            container.setDomainname(name);
            return this;
        }

        public Builder withImage(String image) {
            container.setImage(image);
            return this;
        }

        public Builder withAutoRemove(boolean autoRemove) {
            container.setAutoRemove(autoRemove);
            return this;
        }

        public Builder withTty(boolean tty) {
            container.setTty(tty);
            return this;
        }

        public Builder withOpenStdin(boolean open) {
            container.setOpenStdin(open);
            return this;
        }

        public Builder withEnv(String key, String value) {
            container.getEnv().add(key + "=" + value);
            return this;
        }

        public Builder withVolume(String containerVolume, String sourceVolume) {
            VolumeMount volume = new VolumeMount(containerVolume, sourceVolume, MountType.BIND, false);
            volume.setMode("rw");
            //container.getHostConfig().getBinds().add(sourceVolume + ":" + containerVolume + ":rw");
            container.getConfig().getVolumes().put(containerVolume, new Config.Volume());
            container.getHostConfig().getMounts().add(volume);
            return this;
        }

        public Builder withHostConfig(HostConfig config) {
            container.setHostConfig(config);
            return this;
        }

        public Container build() {
            return container;
        }
    }
}