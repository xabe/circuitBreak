package com.xabe.binary.protocol.model;

import com.xabe.binary.protocol.AbstractPojoTest;
import org.junit.Before;

public class GithubUserTest extends AbstractPojoTest<GithubUser> {

    @Override
    @Before
    public void beforeTests() {
        this.pojo = new GithubUser("OK","OK","OK");
        this.otherPojo = new GithubUser("KO","KO","KO");
        this.equalsPojo = new GithubUser("OK","OK","OK");
    }
}