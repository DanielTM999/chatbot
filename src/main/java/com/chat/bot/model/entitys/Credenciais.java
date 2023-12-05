package com.chat.bot.model.entitys;

import java.util.Collection;
import java.util.List;

import org.hibernate.annotations.Check;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "credenciais")
@Data
@NoArgsConstructor
public class Credenciais implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String usernameClinte;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean isAdm;

    @Column(nullable = false)
    @Check(constraints = "plano >= 0 AND plano <= 2")
    private Integer plano;

    @OneToOne(mappedBy = "credenciais", cascade = CascadeType.ALL)
    private Usuarios usuario;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(isAdm){
            return List.of(new SimpleGrantedAuthority("ADMIN"), new SimpleGrantedAuthority("PLUS"), new SimpleGrantedAuthority("BASIC"));
        }else if(plano > 1){
            return List.of(new SimpleGrantedAuthority("PLUS"), new SimpleGrantedAuthority("BASIC"));
        }
        
        return  List.of(new SimpleGrantedAuthority("BASIC"));
    }

    @Override
    public String getPassword() {
       return password;
    }

    @Override
    public String getUsername() {
        return usernameClinte;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {

        if(isAdm){
            return true;
        }else if(plano > 0){
            return true;
        }

        return false;

    }
}
