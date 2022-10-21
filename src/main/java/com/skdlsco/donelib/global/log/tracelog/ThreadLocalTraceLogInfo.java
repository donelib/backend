package com.skdlsco.donelib.global.log.tracelog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NamedThreadLocal;

import java.util.List;

@Slf4j
public class ThreadLocalTraceLogInfo {
    private static final ThreadLocal<TraceLogInfo> threadLocal = new NamedThreadLocal<>("Current TraceLogInfo");

    public static TraceLogInfo get() {
        initIfNull();
        return threadLocal.get();
    }

    private static void initIfNull() {
        if (threadLocal.get() == null)
            threadLocal.set(new TraceLogInfo());
    }

    public static void remove() {
        threadLocal.remove();
    }

    public static List<String> getLogs() {
        return get().getLogs();
    }

    public static int getDepth() {
        return get().getDepth();
    }

    public static void addDepth() {
        get().addDepth();
    }

    public static void removeDepth() {
        get().removeDepth();
    }

    public static boolean isFirstDepth() {
        return getDepth() == 1;
    }

    public static void startTime() {
        get().setStartTimeMs(System.currentTimeMillis());
    }

    public static long getStartTime() {
        return get().getStartTimeMs();
    }

    public static String getId() {
        return get().getId();
    }

    public static void setErrorType(ErrorType errorType) {
        get().setErrorType(errorType);
    }

    public static ErrorType getErrorType() {
        return get().getErrorType();
    }

    public static void setException(Throwable e) {
        get().setException(e);
    }

    public static Throwable getException() {
        return get().getException();
    }
}
