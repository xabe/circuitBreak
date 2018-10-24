package com.xabe.binary.protocol.model;

import com.xabe.binary.protocol.AbstractPojoTest;
import org.junit.jupiter.api.BeforeEach;

public class GithubUserTest extends AbstractPojoTest<GithubUser> {

    @Override
    @BeforeEach
    public void beforeTests() {
        this.pojo = new GithubUser("OK","OK","OK");
        this.otherPojo = new GithubUser("KO","KO","KO");
        this.equalsPojo = new GithubUser("OK","OK","OK");
    }
}