package com.skdlsco.donelib.global.log.appender;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import ch.qos.logback.core.encoder.Encoder;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiscordAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {
    private final static ObjectMapper objectMapper = new ObjectMapper();
    private Encoder<ILoggingEvent> encoder;
    private String webhookUrl;
    private static final int DEFAULT_COLOR = -1;
    private int traceColor = DEFAULT_COLOR;
    private int debugColor = DEFAULT_COLOR;
    private int infoColor = DEFAULT_COLOR;
    private int warnColor = DEFAULT_COLOR;
    private int errorColor = DEFAULT_COLOR;
    private int timeout = 30_000;

    @Override
    protected void append(ILoggingEvent eventObject) {
        if (webhookUrl == null || encoder == null)
            return;
        try {
            sendMessage(eventObject);
        } catch (Exception e) {
            e.printStackTrace();
            addError("Error posting  log to discord", e);
        }
    }

    private void sendMessage(ILoggingEvent eventObject) throws IOException {
        byte[] data = createBody(eventObject);

        postMessage(webhookUrl, data);
    }

    private byte[] createBody(ILoggingEvent eventObject) throws IOException {
        String message = new String(encoder.encode(eventObject));
        Map<String, Object> embed = new HashMap<>();

        embed.put("description", message);
        int color = getColorByLevel(eventObject.getLevel());
        if (color != -1)
            embed.put("color", color);

        Map<String, Object> body = new HashMap<>();
        body.put("embeds", List.of(embed));

        return objectMapper.writeValueAsBytes(body);
    }

    private int getColorByLevel(Level level) {
        return switch (level.levelInt) {
            case Level.TRACE_INT -> traceColor;
            case Level.DEBUG_INT -> debugColor;
            case Level.INFO_INT -> infoColor;
            case Level.WARN_INT -> warnColor;
            case Level.ERROR_INT -> errorColor;
            default -> DEFAULT_COLOR;
        };
    }

    private void postMessage(String uri, byte[] bytes) throws IOException {
        final HttpURLConnection conn = (HttpURLConnection) new URL(uri).openConnection();
        conn.setConnectTimeout(timeout);
        conn.setReadTimeout(timeout);
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setFixedLengthStreamingMode(bytes.length);
        conn.setRequestProperty("Content-Type", "application/json");

        final OutputStream os = conn.getOutputStream();
        os.write(bytes);

        os.flush();
        os.close();
    }

    public Encoder<ILoggingEvent> getEncoder() {
        return encoder;
    }

    public void setEncoder(Encoder<ILoggingEvent> encoder) {
        this.encoder = encoder;
    }

    public String getWebhookUrl() {
        return webhookUrl;
    }

    public void setWebhookUrl(String webhookUrl) {
        this.webhookUrl = webhookUrl;
    }

    public int getTraceColor() {
        return traceColor;
    }

    public void setTraceColor(int traceColor) {
        this.traceColor = traceColor;
    }

    public int getDebugColor() {
        return debugColor;
    }

    public void setDebugColor(int debugColor) {
        this.debugColor = debugColor;
    }

    public int getInfoColor() {
        return infoColor;
    }

    public void setInfoColor(int infoColor) {
        this.infoColor = infoColor;
    }

    public int getWarnColor() {
        return warnColor;
    }

    public void setWarnColor(int warnColor) {
        this.warnColor = warnColor;
    }

    public int getErrorColor() {
        return errorColor;
    }

    public void setErrorColor(int errorColor) {
        this.errorColor = errorColor;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
