package com.brunadelmouro.microservicemeetup.services.impl;

import com.brunadelmouro.microservicemeetup.exceptions.ObjectNotFoundException;
import com.brunadelmouro.microservicemeetup.models.Registration;
import com.brunadelmouro.microservicemeetup.repositories.RegistrationRepository;
import com.brunadelmouro.microservicemeetup.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private RegistrationRepository repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Registration> obj = repo.findByEmail(email);

        if (obj.isEmpty())
            throw new ObjectNotFoundException("Not Found");

        return new UserSS(obj.get().getId(), obj.get().getEmail(), obj.get().getPassword(), obj.get().getProfile());
    }
}
