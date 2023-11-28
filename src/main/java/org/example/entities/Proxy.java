package org.example.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "proxies")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Proxy {


    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "service_id")
    private int serviceId;

    private String ip;

    private String login;

    private String password;

    @Column(name = "is_valide")
    private boolean isValide = true;

    @Column(name = "expire_date")
    private LocalDate expireDate;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    public Proxy(int serviceId, String ip, LocalDate expireDate) {
        this.serviceId = serviceId;
        this.ip = ip;
        this.expireDate = expireDate;
    }
}
