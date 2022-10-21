package com.skdlsco.donelib.global.log.tracelog;

public interface TraceLogManager {
    void begin(String message);

    void end(String message);

    void exception(String message, Throwable ex);

    void setException(Throwable e);

    Throwable getException();

    void setSlowTime(long slowTimeMs);

    long getResponseTime();

    ErrorType getErrorType();

    void setErrorType(ErrorType errorType);

    void clear();
}
