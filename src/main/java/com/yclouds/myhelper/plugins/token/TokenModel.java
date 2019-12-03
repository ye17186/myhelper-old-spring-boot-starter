package com.yclouds.myhelper.plugins.token;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * TokenModel
 *
 * @author yemeng-lhq
 * @version 2019/12/3 14:31
 */
@Setter
@Getter
public class TokenModel implements Serializable {

    private static final long serialVersionUID = -6944842431417716885L;
    private String token;

    private String timestamp;

    private String nonce;

    private String signature;

    public TokenModel(String token, String timestamp, String nonce, String signature) {
        this.token = token;
        this.timestamp = timestamp;
        this.nonce = nonce;
        this.signature = signature;
    }
}
