package com.musicexchange.config;

import com.musicexchange.repository.ArtistRepository;
import com.musicexchange.repository.FanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final ArtistRepository artistRepository;
    private final FanRepository fanRepository;

    @Override
    public UserDetails loadUserByUsername(String username){
        var artistOpt = artistRepository.findByUsername(username);
        if(artistOpt.isPresent()) {
            var artist = artistOpt.get();
            return User.builder()
                    .username(artist.getUsername())
                    .password(artist.getPassword())
                    .roles("ARTIST")
                    .build();
        }
            var fanOpt = fanRepository.findByUsername(username);
            if(fanOpt.isPresent()){
                var fan = fanOpt.get();
                return User.builder()
                        .username(fan.getUsername())
                        .password(fan.getPassword())
                        .roles("FAN")
                        .build();
            }

            throw new UsernameNotFoundException("Username not found for :" + username);
        }

}
