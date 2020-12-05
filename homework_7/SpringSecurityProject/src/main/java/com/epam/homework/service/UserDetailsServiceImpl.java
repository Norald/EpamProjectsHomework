package com.epam.homework.service;

import com.epam.homework.model.User;
import com.epam.homework.repo.UserRepository;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger LOG = LogManager.getLogger(UserDetailsServiceImpl.class.getName());

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(s);
        if (user == null) {
            throw new UsernameNotFoundException("User " + user.getEmail() + " was not found in the database");
        }
        LOG.info("Found User: " + user);

        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        ;
        if (user.getUserRoleId() == 2) {
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_ADMIN");
            GrantedAuthority authority2 = new SimpleGrantedAuthority("ROLE_USER");
            grantList.add(authority);
            grantList.add(authority2);
        } else {
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
            grantList.add(authority);
        }

        UserDetails userDetails = new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return grantList;
            }

            @Override
            public String getPassword() {
                return user.getPassword();
            }

            @Override
            public String getUsername() {
                return user.getEmail();
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
                return true;
            }
        };
        return userDetails;
    }
}
