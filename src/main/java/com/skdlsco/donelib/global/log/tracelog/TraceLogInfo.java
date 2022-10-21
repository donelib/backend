package com.skdlsco.donelib.global.log.tracelog;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class TraceLogInfo {
    private String id = generateId();
    private int depth;
    private Throwable exception;
    private ErrorType errorType;
    private long startTimeMs = System.currentTimeMillis();
    private List<String> logs = new ArrayList<>();

    public void setException(Throwable exception) {
        this.exception = exception;
    }

    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }

    public void setStartTimeMs(long startTimeMs) {
        this.startTimeMs = startTimeMs;
    }

    public void addDepth() {
        this.depth++;
    }

    public void removeDepth() {
        this.depth--;
    }

    private String generateId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
