package com.client_ws.rasmooplus.service.impl;

import com.client_ws.rasmooplus.controller.SubscriptionTypeController;
import com.client_ws.rasmooplus.dto.SubscriptionTypeDto;
import com.client_ws.rasmooplus.exception.BadRequestException;
import com.client_ws.rasmooplus.exception.NotFoundException;
import com.client_ws.rasmooplus.mapper.SubscriptionTypeMapper;
import com.client_ws.rasmooplus.model.SubscriptionType;
import com.client_ws.rasmooplus.repository.SubscriptionTypeRepository;
import com.client_ws.rasmooplus.service.SubscriptionTypeService;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SubscriptionTypeServiceImpl implements SubscriptionTypeService {

    private final SubscriptionTypeRepository subscriptionTypeRepository;

    public SubscriptionTypeServiceImpl(SubscriptionTypeRepository subscriptionTypeRepository) {
        this.subscriptionTypeRepository = subscriptionTypeRepository;
    }

    @Override
    public List<SubscriptionType> findAll() {
        return subscriptionTypeRepository.findAll();
    }

    @Override
    public SubscriptionType findById(Long id) {
        return getSubscriptionType(id).add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(SubscriptionTypeController.class)
                        .findById(id)).withSelfRel()
        ).add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(SubscriptionTypeController.class)
                        .update(id, new SubscriptionTypeDto())).withRel("update")
        ).add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(SubscriptionTypeController.class)
                        .delete(id)).withRel("delete")
        );
    }


    @Override
    public SubscriptionType create(SubscriptionTypeDto dto) {
        if (Objects.nonNull(dto.getId())) {
            throw new BadRequestException("O id deve ser nulo");
        }
        return subscriptionTypeRepository.save(SubscriptionTypeMapper.fromDtoEntity(dto));
    }

    @Override
    public SubscriptionType update(Long id, SubscriptionTypeDto dto) {
        getSubscriptionType(id);
        dto.setId(id);
        return subscriptionTypeRepository.save(SubscriptionTypeMapper.fromDtoEntity(dto));
    }

    @Override
    public void delete(Long id) {
        getSubscriptionType(id);
        subscriptionTypeRepository.deleteById(id);
    }

    private SubscriptionType getSubscriptionType(Long id) {
        Optional<SubscriptionType> optionalSubscriptionType = subscriptionTypeRepository.findById(id);
        if (optionalSubscriptionType.isEmpty()) {
            throw new NotFoundException("SubscritpionType não encontrado");
        }
        return optionalSubscriptionType.get();
    }
}
