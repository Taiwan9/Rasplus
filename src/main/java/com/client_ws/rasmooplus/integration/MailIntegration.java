package com.client_ws.rasmooplus.integration;

public interface MailIntegration {
    void send(String mailTo, String message, String subject);
}
