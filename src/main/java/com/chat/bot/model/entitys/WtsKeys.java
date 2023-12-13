package com.chat.bot.model.entitys;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "wtskeys")
@Data
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

    @Override
    public String toString() {
        return "WtsKeys [id=" + id + ", apiKey=" + apiKey + ", apiToken=" + apiToken + "]";
    }

}
