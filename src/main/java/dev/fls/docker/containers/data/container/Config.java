package dev.fls.docker.containers.data.container;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;


@NoArgsConstructor
@Getter
public class Config {

    private String Hostname, Domainname, User;
    private boolean AttachStdin, AttachStdout, AttachStderr;

    private final Map<String, Volume> Volumes = new HashMap<>();

    public static class Volume {

    }
}
