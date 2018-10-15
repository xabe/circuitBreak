package com.xabe.binary.protocol.service;

import com.xabe.binary.protocol.model.GithubUser;

import java.util.Optional;

public interface GithubService {

    Optional<GithubUser> getUser(String user);
}
