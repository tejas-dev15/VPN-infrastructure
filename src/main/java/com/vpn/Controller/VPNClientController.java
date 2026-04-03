package com.vpn.Controller;
import com.vpn.DTO.VPNClientResponse;
import com.vpn.Entity.VPNClient;
import com.vpn.Service.SSHService;
import com.vpn.Service.VPNClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/vpn")
@RequiredArgsConstructor
public class VPNClientController {

    private final VPNClientService vpnClientService;
    private final SSHService sshService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/Create-Client")
    public VPNClientResponse CreateClient(@RequestParam Long id){
         return vpnClientService.createVpnClient(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/GetAllClients")
    public List<VPNClientResponse> getAllClients(){
        return vpnClientService.getAllClients();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("{id}")
    public VPNClientResponse getVPNClient(@PathVariable Long id){
        return vpnClientService.getClientById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public void DeleteVPNClient(@PathVariable Long id){
        vpnClientService.DeleteClient(id);
    }

    @GetMapping("/config/{id}")
    public String GetConfig(@PathVariable Long id){
        return vpnClientService.generate_Config(id);
    }

    @GetMapping(value ="/{id}/qr", produces= "image/png" )
    public byte[] get_QR(@PathVariable Long id){
        return vpnClientService.QR_Generator(id);
    }
}
