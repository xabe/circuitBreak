package com.xabe.binary.protocol.connector;

import java.util.Optional;

public interface RestConnector {

    <T> Optional<T> getSimpleObject(ClientRequestInfo clientRequestInfo);
}
