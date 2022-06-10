package dev.fls.spdocker.docker;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public enum ApiVersion {


    V1_41(),

    @Deprecated
    V1_40(),
    @Deprecated
    V1_39(),
    @Deprecated
    V1_38(),
    @Deprecated
    V1_37(),
    @Deprecated
    V1_36(),
    @Deprecated
    V1_35(),
    @Deprecated
    V1_34(),
    @Deprecated
    V1_33(),
    @Deprecated
    V1_32(),
    @Deprecated
    V1_31(),
    @Deprecated
    V1_30(),
    @Deprecated
    V1_29(),
    @Deprecated
    V1_28(),
    @Deprecated
    V1_27(),
    @Deprecated
    V1_26(),
    @Deprecated
    V1_25(),
    @Deprecated
    V1_24(),
    @Deprecated
    V1_23(),
    @Deprecated
    V1_22(),
    @Deprecated
    V1_21(),
    @Deprecated
    V1_20(),
    @Deprecated
    V1_19(),
    @Deprecated
    V1_18(),
    ;


    /**
     * Get all versions with a range
     * @param from Start version
     * @param to End version
     * @return
     */
    public static List<ApiVersion> get(ApiVersion from, ApiVersion to) {
        List<ApiVersion> versions = Arrays.asList(ApiVersion.values());
        List<ApiVersion> list = new ArrayList<>();

        exchangeVersions: {
            if(versions.indexOf(to) >= versions.indexOf(from)) break exchangeVersions;

            ApiVersion temporaryFrom = from;

            from = to;
            to = temporaryFrom;
        }

        for(ApiVersion apiVersion : versions) {
            if(versions.indexOf(apiVersion) < versions.indexOf(from) || (versions.indexOf(apiVersion) > versions.indexOf(to))) continue;

            list.add(apiVersion);
        }

        return list;
    }
}
