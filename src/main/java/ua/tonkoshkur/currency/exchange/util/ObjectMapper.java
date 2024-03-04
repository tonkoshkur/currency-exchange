package ua.tonkoshkur.currency.exchange.util;

import java.util.List;

public interface ObjectMapper<D, E> {

    D toDto(E entity);

    E toEntity(D dto);

    default List<D> toDto(List<E> entityList) {
        return entityList.stream()
                .map(this::toDto)
                .toList();
    }

    default List<E> toEntity(List<D> dtoList) {
        return dtoList.stream()
                .map(this::toEntity)
                .toList();
    }
}
