package co.com.sofka.persona.services.commons;

import co.com.sofka.persona.exceptions.ResourceNotFoundException;
import co.com.sofka.persona.mappers.GenericMapper;
import co.com.sofka.persona.persistence.entities.AbstractEntity;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@AllArgsConstructor
public abstract class AbstractService<E extends AbstractEntity<K>, D, K> implements GenericService<D,K> {

    protected JpaRepository<E,K> repository;
    protected GenericMapper<D,E, K> mapper;
    protected String notFoundMessage;


    @Override
    public D save(D dto) {
        var entity = this.mapper.toEntity(dto);
        this.repository.saveAndFlush(entity);
        return this.mapper.toDto(entity);
    }

    @Override
    public List<D> getAll() {

        return this.repository.findAll().stream()
                .map(this.mapper::toDto)
                .toList();

    }

    @Override
    public D getById(K id) {
        return this.repository.findById(id)
                .map(this.mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(this.notFoundMessage));
    }

    @Override
    public void delete(K id) {
          this.repository.deleteById(id);
    }

    @Override
    public D update(D dto, K id) {

        if(!this.repository.existsById(id)) {
            throw new ResourceNotFoundException(this.notFoundMessage);
        }
        var entity = this.mapper.toEntity(dto);
        entity.setId(id);
        return this.mapper.toDto(this.repository.saveAndFlush(entity));
    }
}
