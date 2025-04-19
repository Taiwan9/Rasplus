package com.client_ws.rasmooplus.service.impl;

import com.client_ws.rasmooplus.dto.UserDto;
import com.client_ws.rasmooplus.exception.BadRequestException;
import com.client_ws.rasmooplus.exception.NotFoundException;
import com.client_ws.rasmooplus.mapper.UserMapper;
import com.client_ws.rasmooplus.model.jpa.User;
import com.client_ws.rasmooplus.model.jpa.UserType;
import com.client_ws.rasmooplus.model.redis.UserRecoveryCode;
import com.client_ws.rasmooplus.repository.jpa.UserRepository;
import com.client_ws.rasmooplus.repository.jpa.UserTypeRepository;
import com.client_ws.rasmooplus.repository.redis.UserRecoveryCodeRepository;
import com.client_ws.rasmooplus.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserTypeRepository userTypeRepository;

    public UserServiceImpl(UserRepository userRepository, UserTypeRepository userTypeRepository) {
        this.userRepository = userRepository;
        this.userTypeRepository = userTypeRepository;
    }

    @Override
    public User create(UserDto dto) {

        if (Objects.nonNull(dto.getId())) {
            throw new BadRequestException("id deve ser nulo");
        }

        var userTypeOpt = userTypeRepository.findById(dto.getUserTypeId());

        if (userTypeOpt.isEmpty()) {
            throw new NotFoundException("userTypeId não encontrado");
        }

        UserType userType = userTypeOpt.get();
        User user = UserMapper.fromDtoToEntity(dto, userType, null);
        return userRepository.save(user);
    }

    @Override
    public User uploadPhoto(Long id, MultipartFile file) throws IOException {
        String imgName = file.getOriginalFilename();

        assert imgName != null;
        String lower = imgName.toLowerCase();
        if (!(lower.endsWith(".png") || lower.endsWith(".jpeg"))) {
            throw new BadRequestException("Imagem deve possuir o formato JPEG ou PNG");
        }
        User user;
        user = findById(id);
        user.setPhotoName(file.getOriginalFilename());
        user.setPhoto(file.getBytes());
        return userRepository.save(user);
    }

    @Override
    public byte[] downloadPhoto(Long id) {
        User user = findById(id);
        if(Objects.isNull(user.getPhoto())){
            throw new BadRequestException("Usuário não possui foto");
        }
        return user.getPhoto();
    }

    private User findById(Long id){
        return userRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Usuario não encontado"));
    }

}
