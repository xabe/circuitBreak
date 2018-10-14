package com.xabe.binary.protocol.connector;

public interface RestConnector {
    <T> T getSimpleObject(ClientRequestInfo clientRequestInfo);
}
