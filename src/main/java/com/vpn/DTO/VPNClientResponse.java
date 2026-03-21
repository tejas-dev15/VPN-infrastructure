package com.vpn.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;



public class VPNClientResponse {

    private Long id;
    private String PublicKey;
    private String vpnIP;

    private Long Id;
    private String username;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPublicKey() {
        return PublicKey;
    }

    public void setPublicKey(String publicKey) {
        PublicKey = publicKey;
    }

    public String getVpnIP() {
        return vpnIP;
    }

    public void setVpnIP(String vpnIP) {
        this.vpnIP = vpnIP;
    }
}
