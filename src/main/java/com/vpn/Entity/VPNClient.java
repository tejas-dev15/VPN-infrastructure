package com.vpn.Entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "vpn_clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VPNClient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String PublicKey;
    private String PrivateKey;
    private String vpnIP;

    private LocalDateTime Created_At;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @PrePersist
    public void PrePersist(){
        this.Created_At = LocalDateTime.now();
    }
}
