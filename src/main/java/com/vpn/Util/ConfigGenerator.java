package com.vpn.Util;

import com.vpn.Entity.VPNClient;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ConfigGenerator {

    private final String Server_IP = "192.168.0.104";
    String server_PublicKey = "OHJvxbaakTfXdZ8jrVqEd91pAWJSvlO5az5yQ+o5sAs=";

    public String Generate_config(VPNClient client){


        return String.format("""
                [Interface]
                PrivateKey = %s
                Address = %s/32
                DNS = 8.8.8.8
                MTU = 1380
                
                [Peer]
                PublicKey = %s
                Endpoint = %s:51820
                AllowedIPs = 0.0.0.0/0
                PersistentKeepalive = 25
                """,
                client.getPrivateKey(),
                client.getVpnIP(),
                server_PublicKey,
                  Server_IP);
    }
}
