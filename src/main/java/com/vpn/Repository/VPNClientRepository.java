package com.vpn.Repository;

import com.vpn.Entity.VPNClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VPNClientRepository extends JpaRepository<VPNClient, Long> {
      Optional<VPNClient> findById(Long id);
      Optional<VPNClient> findByVpnIP(String vpnIP);
}
