package dev.fls.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HttpResponseCode {

    INFO(100, 199),
    SUCCESS(200, 299),
    REDIRECT(300, 399),
    CLIENT_ERROR(400, 499),
    SERVER_ERROR(500, 599)
    ;

    private final int start, end;

    public static HttpResponseCode get(int code) {
        for (HttpResponseCode value : HttpResponseCode.values()) {
            if(code < value.start || code > value.end) continue;

            return value;
        }

        return null;
    }
}
