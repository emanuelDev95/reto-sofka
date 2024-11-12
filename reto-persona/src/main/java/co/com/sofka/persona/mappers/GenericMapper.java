package co.com.sofka.persona.mappers;

import co.com.sofka.persona.persistence.entities.AbstractEntity;

public interface GenericMapper<D,E extends AbstractEntity<K>, K> {

    E toEntity(D dto);

    D toDto(E entity);


}
