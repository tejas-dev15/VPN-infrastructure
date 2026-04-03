package com.vpn.Service;

import com.vpn.DTO.VPNClientResponse;
import com.vpn.Entity.User;
import com.vpn.Entity.VPNClient;
import com.vpn.Exception.ClientNotFoundException;
import com.vpn.Repository.UserRepository;
import com.vpn.Repository.VPNClientRepository;
import com.vpn.Util.ConfigGenerator;
import com.vpn.Util.IpAllocator;
import com.vpn.Util.QRCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class VPNClientService {

    private final UserRepository userRepository;
    private final VPNClientRepository vpnClientRepository;
    private final IpAllocator ipAllocator;
    private final SSHService sshService;
    private final ConfigGenerator configGenerator;
    private final QRCodeGenerator qrCodeGenerator;

    public VPNClientResponse createVpnClient(Long Id){
        User user = userRepository.findById(Id)
                .orElseThrow(()-> new ClientNotFoundException("Client not found"));

        String privateKey = sshService.generatePrivateKey();
        String PublicKey = sshService.generatePublicKey(privateKey);
        String ip = ipAllocator.IpAllocate();
        sshService.AddPeer(PublicKey, ip);

        VPNClient client = VPNClient.builder()
                           .user(user)
                           .PublicKey(PublicKey)
                           .PrivateKey(privateKey)
                           .vpnIP(ip)
                           .build();

        VPNClient saved = vpnClientRepository.save(client);
        return  maptoResponse(saved, true);
    }

    public List<VPNClientResponse> getAllClients() {

        return vpnClientRepository.findAll()
                .stream()
                .map(client -> maptoResponse(client,false))
                .toList();
    }

    public VPNClientResponse getClientById(Long id) {

        VPNClient client = vpnClientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client not found"));

       return maptoResponse(client, true);
    }

    public void DeleteClient(Long id){
        VPNClient Client = vpnClientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client not found"));
        try {
            sshService.removePeer(Client.getPublicKey());
        } catch (Exception e) {
            throw new RuntimeException("Failed to Remove Peer form Wireguard");
        }
         vpnClientRepository.deleteById(id);
    }

    public String generate_Config(Long id){
        VPNClient client = vpnClientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("client not found"));

        return configGenerator.Generate_config(client);
    }

    public byte[] QR_Generator(Long id){
        VPNClient client = vpnClientRepository.findById(id)
                .orElseThrow(()-> new ClientNotFoundException("Client not found"));

        String config = configGenerator.Generate_config(client);

        return qrCodeGenerator.generate_QR(config);
    }

    public VPNClientResponse maptoResponse(VPNClient client,boolean includePrivateKey){
        VPNClientResponse response = new VPNClientResponse();

        response.setId(client.getId());
        response.setUserId(client.getUser().getId());
        response.setUsername(client.getUser().getUsername());
        response.setVpnIP(client.getVpnIP());
        response.setPublicKey(client.getPublicKey());

        if(includePrivateKey){
            response.setPrivateKey(client.getPrivateKey());
        }
        return response;
    }
}
