package com.vpn.Service;

import com.vpn.DTO.VPNClientResponse;
import com.vpn.Entity.User;
import com.vpn.Entity.VPNClient;
import com.vpn.Repository.UserRepository;
import com.vpn.Repository.VPNClientRepository;
import com.vpn.Util.IpAllocator;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
@RequiredArgsConstructor
public class VPNClientService {

    private final UserRepository userRepository;
    private final VPNClientRepository vpnClientRepository;
    private final IpAllocator ipAllocator;

    public VPNClientResponse CreateVpnClient(Long Id){
        User user = userRepository.findById(Id)
                .orElseThrow(()-> new RuntimeException("Client not found"));

        String ip = ipAllocator.IpAllocate();

        VPNClient client = VPNClient.builder()
                           .user(user)
                           .PublicKey("TEMP_KEY")
                           .vpnIP(ip)
                           .build();

        VPNClient saved = vpnClientRepository.save(client);

        VPNClientResponse response = new VPNClientResponse();

        response.setId(saved.getId());
        response.setVpnIP(saved.getVpnIP());
        response.setPublicKey(saved.getPublicKey());

        response.setId(saved.getUser().getId());
        response.setUsername(saved.getUser().getUsername());

        
        return  response;
    }

    public List<VPNClientResponse> getAllClients() {

        List<VPNClient> clients = vpnClientRepository.findAll();

        return clients.stream().map(client -> {

            VPNClientResponse response = new VPNClientResponse();

            response.setId(client.getId());
            response.setVpnIP(client.getVpnIP());
            response.setPublicKey(client.getPublicKey());

            response.setId(client.getUser().getId());
            response.setUsername(client.getUser().getUsername());

            return response;

        }).toList();
    }

    public VPNClientResponse getClientById(Long id) {

        VPNClient client = vpnClientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        VPNClientResponse response = new VPNClientResponse();

        response.setId(client.getId());
        response.setVpnIP(client.getVpnIP());
        response.setPublicKey(client.getPublicKey());

        response.setId(client.getUser().getId());
        response.setUsername(client.getUser().getUsername());

        return response;
    }

    public void DeleteClient(Long id){
         vpnClientRepository.deleteById(id);
    }
}
