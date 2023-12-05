package com.chat.bot.services.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.chat.bot.model.repositories.Repositorys;



@Service
public class AutorizationService implements UserDetailsService{

    @Autowired
    private Repositorys repositorys;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repositorys.getCredenciaisRepository().findByUsernameClinte(username);
    }
    
}
