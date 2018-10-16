package com.xabe.binary.protocol.connector;

import com.xabe.binary.protocol.connector.builder.ClientRequestInfo;

import java.util.Optional;

public interface RestConnector {

    <T> Optional<T> getSimpleObject(ClientRequestInfo clientRequestInfo);
}
