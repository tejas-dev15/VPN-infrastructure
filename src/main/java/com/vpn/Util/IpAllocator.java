package com.vpn.Util;

import com.vpn.Entity.VPNClient;
import com.vpn.Repository.VPNClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class IpAllocator {

    private final VPNClientRepository vpnClientRepository;
    private static String BASE_IP = "10.0.0.";

    public String IpAllocate() {
        List<VPNClient> clients = vpnClientRepository.findAll();

        Set<Integer> used = new HashSet<>();

        for (VPNClient client : clients) {
            String ip = client.getVpnIP();

            if (ip != null) {
                int lastOctet = Integer.parseInt(ip.split("\\.")[3]);
                used.add(lastOctet);
            }
        }

        for(int i =3; i<= 254 ; i++){
            if(!used.contains(i)){
                return BASE_IP + i;
            }
        }
        throw new RuntimeException("IP is not avialable");
    }
}