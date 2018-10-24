package com.xabe.binary.protocol.payload;

import com.xabe.binary.protocol.AbstractPojoTest;
import org.junit.jupiter.api.BeforeEach;

public class GithubUserPayloadTest extends AbstractPojoTest<GithubUserPayload> {

    @Override
    @BeforeEach
    public void beforeTests() {
        this.pojo = new GithubUserPayload("OK","OK","OK");
        this.otherPojo = new GithubUserPayload("KO","KO","KO");
        this.equalsPojo = new GithubUserPayload("OK","OK","OK");
    }
}