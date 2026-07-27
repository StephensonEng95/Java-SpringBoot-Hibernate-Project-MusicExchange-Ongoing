package com.musicexchange.service;

import com.musicexchange.dto.ArtistRequestDto;
import com.musicexchange.dto.FanRequestDto;
import com.musicexchange.dto.FanResponseDto;
import com.musicexchange.exceptions.DuplicateResourceException;
import com.musicexchange.exceptions.ResourceNotFoundException;
import com.musicexchange.models.Fan;
import com.musicexchange.repository.FanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FanService {

    private final FanRepository fanRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public FanResponseDto createFan(ArtistRequestDto requestDto) {
        if (fanRepository.existsByUsername(requestDto.getUsername())) {
            throw new DuplicateResourceException("Username is already taken");
        }

        if (fanRepository.existsByEmail(requestDto.getEmail())) {
            throw new DuplicateResourceException("Email is already taken");
        }

        Fan fan = new Fan();
        fan.setUsername(requestDto.getUsername());
        fan.setEmail(requestDto.getEmail());
        fan.setPassword(passwordEncoder.encode(requestDto.getPassword()));

        Fan savedFan = fanRepository.save(fan);
        return mapToResponseDto(savedFan);
    }

    @Transactional(readOnly = true)
    public FanResponseDto getFanById(Long fanId){
        Fan fan = fanRepository.findById(fanId).orElseThrow(()-> new ResourceNotFoundException("Fan not found with artistId:" + fanId));
        return mapToResponseDto(fanRepository.save(fan));
    }

    public FanResponseDto updateFanByEmail(Long fanId, String email){
            Fan fan = fanRepository.findById(fanId)
                    .orElseThrow(()->new ResourceNotFoundException(("Fan not found with artistId:" + fanId)));
            fan.setEmail(email);
            Fan updatedFan = fanRepository.save(fan);
            return mapToResponseDto(updatedFan);
    }

    public void updateFanByPassword(Long fanId, String password) {
        Fan fan = fanRepository.findById(fanId)
                .orElseThrow(()->new ResourceNotFoundException("Fan not found with : artistId" + fanId));
        if(!passwordEncoder.matches(password, fan.getPassword())){
            throw new ResourceNotFoundException("invalid password");
        }
        fan.setPassword(password);
        fanRepository.save(fan);
    }

    public void deleteFan(Long fanId, FanRequestDto requestDto){
        Fan fan = fanRepository.findById(fanId)
                .orElseThrow(()->new ResourceNotFoundException("Can't delete account with :" + fanId));
        fanRepository.delete(fan);

    }

    public void deleteFanByUsername(String username){
        Fan fan = fanRepository.findByUsername(username)
                .orElseThrow(()-> new ResourceNotFoundException("couldn't find fan with username" + username));
        fanRepository.delete(fan);
    }
    private FanResponseDto mapToResponseDto(Fan fan) {
        return new FanResponseDto(
                fan.getFanId(),
                fan.getUsername(),
                fan.getEmail(),
                fan.getProfilePicture(),
                fan.getCreatedAt(),
                fan.getUpdatedAt()
        );
    }
}