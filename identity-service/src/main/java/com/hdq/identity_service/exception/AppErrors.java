package com.hdq.identity_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum AppErrors {
    SUCCESS(200, "msg.success", HttpStatus.OK),
    BAD_REQUEST(400, "msg.bad.request", HttpStatus.BAD_REQUEST),
    BAD_REQUEST_PATH(400, "msg.bad.request.path", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(401, "msg.unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(403, "msg.access.you-do-not-have-permission", HttpStatus.FORBIDDEN),
    INTERNAL_SERVER(500, "msg.internal.server", HttpStatus.INTERNAL_SERVER_ERROR),

    OTP_INCORRECT(9000, "otp.otp-incorrect", HttpStatus.BAD_REQUEST),
    UPLOAD_FAIL(9100,"upload-file.upload-file-fail", HttpStatus.BAD_REQUEST),
    UNCATEGORIZED_EXCEPTION(9200, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),

    RESPONSE_NOT_FOUND(10404, "Response not found", HttpStatus.BAD_REQUEST),
    ID_NOT_MATCH(10400, "Id not match", HttpStatus.BAD_REQUEST);

    private final int code;
    private final String description;
    private final HttpStatusCode statusCode;

    AppErrors(int code, String description, HttpStatusCode statusCode) {
        this.code = code;
        this.description = description;
        this.statusCode = statusCode;
    }

}
