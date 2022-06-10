package dev.fls.spdocker.docker.containers.data.container.volumes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VolumeMount {

    private final String Target, Source, Type;
    private String Mode;
    private final boolean ReadOnly;

    public VolumeMount(String target, String source, MountType type, boolean readOnly) {
        Target = target;
        Source = source;
        Type = type.name().toLowerCase();
        ReadOnly = readOnly;
    }
}
