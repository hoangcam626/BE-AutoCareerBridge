package com.backend.autocarrerbridge.util;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class DataUtil {

    // Chuyển từ Entity sang DTO
    public static <E, D> D toDto(E entity, Class<D> dtoClass) {
        try {
            D dto = dtoClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(entity, dto);
            return dto;
        } catch (Exception e) {
            throw new RuntimeException("Error while mapping Entity to DTO", e);
        }
    }

    // Chuyển từ DTO sang Entity
    public static <E, D> E toEntity(D dto, Class<E> entityClass) {
        try {
            E entity = entityClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(dto, entity);
            return entity;
        } catch (Exception e) {
            throw new RuntimeException("Error while mapping DTO to Entity", e);
        }
    }

    // Chuyển danh sách Entity sang DTO
    public static <E, D> List<D> toDtoList(List<E> entityList, Class<D> dtoClass) {
        return entityList.stream()
                .map(entity -> toDto(entity, dtoClass))
                .collect(Collectors.toList());
    }

    // Chuyển danh sách DTO sang Entity
    public static <E, D> List<E> toEntityList(List<D> dtoList, Class<E> entityClass) {
        return dtoList.stream()
                .map(dto -> toEntity(dto, entityClass))
                .collect(Collectors.toList());
    }
}
