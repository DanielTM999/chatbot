package com.chat.bot.model.entitys;


import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Usuarios {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(nullable = false, unique = true, length = 20)
   private String cnpj;

   @Column(nullable = false)
   private String mainNumber;

   @OneToOne(cascade = CascadeType.ALL)
   @JoinColumn(name = "credenciais_id")
   private Credenciais credenciais;

   @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
   private List<Fluxo> fluxo;

}
