package com.vpn.DTO;

import lombok.Data;


@Data
public class VPNClientResponse {
    private Long id;
    private Long UserId;
    private String PublicKey;
    public String PrivateKey;
    private String vpnIP;
    private String username;


}
