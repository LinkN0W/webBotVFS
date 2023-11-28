package org.example.entities;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue
    private UUID id;


    private String login;


    private String password;


    @OneToMany(mappedBy = "account")
    private List<Proxy> proxies;
}
