package com.xabe.binary.protocol.service;

import com.xabe.binary.protocol.model.GithubUser;

public interface GithubService {

    GithubUser getUser(String user);
}
