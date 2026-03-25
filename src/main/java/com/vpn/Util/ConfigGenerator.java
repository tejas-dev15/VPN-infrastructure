package com.vpn.Util;

import com.vpn.Entity.VPNClient;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ConfigGenerator {

    private final String Server_IP = "192.168.0.102";
    String server_PublicKey = "OHJvxbaakTfXdZ8jrVqEd91pAWJSvlO5az5yQ+o5sAs=";

    public String Generate_config(VPNClient client){


        return String.format("""
                [Interface]
                PrivateKey = %s
                Address = %s/24
                DNS = 1.1.1.1
                
                [Peer]
                PublicKey = %s
                Endpoint = %s:51820
                Allowed IPs = 0.0.0.0
                PersistentKeepalive = 25
                """,
                client.getPrivateKey(),
                client.getVpnIP(),
                server_PublicKey,
                  Server_IP);
    }
}
