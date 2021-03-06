package com.xabe.binary.protocol.payload;

import com.xabe.binary.protocol.AbstractPojoTest;
import org.junit.jupiter.api.BeforeEach;

public class StatusPayloadTest extends AbstractPojoTest<StatusPayload> {

    @Override
    @BeforeEach
    public void beforeTests() {
        this.pojo = new StatusPayload("OK");
        this.otherPojo = new StatusPayload("KO");
        this.equalsPojo = new StatusPayload("OK");
    }
}