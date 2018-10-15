package com.xabe.binary.protocol.payload;

import com.xabe.binary.protocol.AbstractPojoTest;
import org.junit.Before;

public class GithubUserPayloadTest extends AbstractPojoTest<GithubUserPayload> {

    @Override
    @Before
    public void beforeTests() {
        this.pojo = new GithubUserPayload("OK","OK","OK");
        this.otherPojo = new GithubUserPayload("KO","KO","KO");
        this.equalsPojo = new GithubUserPayload("OK","OK","OK");
    }
}