package com.vpn.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(unique = true, nullable = false )
    private String username;

    @Column(nullable = false)
    private String hashpassword;

    @Column(nullable = false)
    private String role;

    private LocalDateTime Created_At;

    @OneToMany(mappedBy = "user")
    private List<VPNClient> vpnClients;

    @PrePersist
    public void PrePersist(){
        this.Created_At = LocalDateTime.now();
    }

}
