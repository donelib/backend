package com.skdlsco.donelib.global.log.tracelog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

@Component
@Slf4j
public class TraceLogManagerImpl implements TraceLogManager {
    @Value("${trace-log.slow-time:10000}")
    private long slowTimeMs;

    @Override
    public void begin(String message) {
        ThreadLocalTraceLogInfo.addDepth();
        if (ThreadLocalTraceLogInfo.isFirstDepth()) {
            ThreadLocalTraceLogInfo.startTime();
        }

        String depthMessage = addTraceIdAndBeginSpace() + message;
        ThreadLocalTraceLogInfo.getLogs().add(depthMessage);
    }

    @Override
    public void end(String message) {
        String depthMessage = addTraceIdAndEndSpace() + message;
        ThreadLocalTraceLogInfo.getLogs().add(depthMessage);

        if (ThreadLocalTraceLogInfo.isFirstDepth()) {
            print();
            clear();
        } else {
            ThreadLocalTraceLogInfo.removeDepth();
        }
    }

    @Override
    public void exception(String message, Throwable ex) {
        String depthMessage = addTraceIdAndExceptionSpace() + message;
        ThreadLocalTraceLogInfo.getLogs().add(depthMessage);

        if (ThreadLocalTraceLogInfo.isFirstDepth()) {
            setException(ex);
            print();
            clear();
        } else {
            ThreadLocalTraceLogInfo.removeDepth();
        }
    }

    public void print() {
        long responseTime = getResponseTime();

        if (log.isTraceEnabled()) {
            log.trace(buildTrace());
        }

        if (responseTime >= slowTimeMs) {
            log.info(buildTrace()); // warn?
        }

        if (getException() != null) {
            printException();
        }
    }

    private void printException() {
        if (getErrorType() == ErrorType.USER) {
            String result = buildTraceAndExceptionLog();
            log.info(result);
        } else if (getErrorType() == ErrorType.APP) {
            String result = buildTraceAndExceptionLog();
            log.error(result);
        }
    }

    private String buildTrace() {
        StringBuilder sb = new StringBuilder();

        buildTraceLog(sb);
        return sb.toString();
    }

    private String buildTraceAndExceptionLog() {
        StringBuilder sb = new StringBuilder();

        buildTraceLog(sb);
        if (getException() != null)
            buildExceptionLog(sb);
        return sb.toString();
    }

    private void buildExceptionLog(StringBuilder sb) {
        if (getException() != null)
            sb.append("[EXCEPTION] ").append(getExceptionTrace());
    }

    private String getExceptionTrace() {
        Throwable e = getException();
        if (e == null)
            return null;

        StringWriter sw = new StringWriter();
        if (e.getMessage() != null) {
            sw.write(e.getMessage() + " ");
        }
        sw.write("stackTrace=");
        getException().printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    private void buildTraceLog(StringBuilder sb) {
        List<String> logs = ThreadLocalTraceLogInfo.getLogs();

        for (int i = 0; i < logs.size(); i++) {
            String log = logs.get(i);
            sb.append(log);
            if (i != logs.size() - 1)
                sb.append("\n");
        }
    }

    private String addTraceIdAndBeginSpace() {
        return "[" + getTraceId() + "]" + addSpace("-->");
    }

    private String addTraceIdAndEndSpace() {
        return "[" + getTraceId() + "]" + addSpace("<--");
    }

    private String addTraceIdAndExceptionSpace() {
        return "[" + getTraceId() + "]" + addSpace("<X-");
    }

    public String getTraceId() {
        return ThreadLocalTraceLogInfo.getId();
    }

    private String addSpace(String prefix) {
        int depth = ThreadLocalTraceLogInfo.getDepth();
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < depth; i++) {
            sb.append(i == depth - 1 ? "|" + prefix : "|   ");
        }
        return sb.toString();
    }

    @Override
    public void clear() {
        ThreadLocalTraceLogInfo.remove();
    }

    @Override
    public void setException(Throwable e) {
        ThreadLocalTraceLogInfo.setException(e);
    }

    @Override
    public Throwable getException() {
        return ThreadLocalTraceLogInfo.getException();
    }

    @Override
    public void setSlowTime(long slowTimeMs) {
        this.slowTimeMs = slowTimeMs;
    }

    @Override
    public long getResponseTime() {
        return System.currentTimeMillis() - ThreadLocalTraceLogInfo.getStartTime();
    }

    @Override
    public ErrorType getErrorType() {
        return ThreadLocalTraceLogInfo.getErrorType();
    }

    @Override
    public void setErrorType(ErrorType errorType) {
        ThreadLocalTraceLogInfo.setErrorType(errorType);
    }
}
