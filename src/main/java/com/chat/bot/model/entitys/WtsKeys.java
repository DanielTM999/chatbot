package com.chat.bot.model.entitys;

import jakarta.persistence.*;

@Entity
@Table(name = "wtskeys")
public class WtsKeys {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    @Column(nullable = false)
    private String apiKey;

    @Column(nullable = false)
    private String apiToken;

    @OneToOne(mappedBy = "keys", cascade = CascadeType.ALL)
    private Usuarios usuario;
}
