package com.vpn.Service;

import com.vpn.DTO.VPNClientResponse;
import com.vpn.Entity.User;
import com.vpn.Entity.VPNClient;
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

    public VPNClientResponse CreateVpnClient(Long Id){
        User user = userRepository.findById(Id)
                .orElseThrow(()-> new RuntimeException("Client not found"));

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

        VPNClientResponse response = new VPNClientResponse();

        response.setId(saved.getId());
        response.setVpnIP(saved.getVpnIP());
        response.setPublicKey(saved.getPublicKey());
        response.setPrivateKey(saved.getPrivateKey());
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
            response.setPrivateKey(client.getPrivateKey());
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
        response.setPrivateKey(client.getPrivateKey());
        response.setId(client.getUser().getId());
        response.setUsername(client.getUser().getUsername());

        return response;
    }

    public void DeleteClient(Long id){
        Optional<VPNClient> Client = Optional.ofNullable(vpnClientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found")));

        sshService.removePeer(Client.get().getPublicKey());

         vpnClientRepository.deleteById(id);
    }

    public String generate_Config(Long id){
        VPNClient client = vpnClientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("client not found"));

        return configGenerator.Generate_config(client);
    }

    public byte[] QR_Generator(Long id){
        VPNClient client = vpnClientRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Client not found"));

        String config = configGenerator.Generate_config(client);

        return qrCodeGenerator.generate_QR(config);
    }
}
