package co.com.sofka.persona.services.commons;

import co.com.sofka.persona.exceptions.ResourceNotFoundException;
import co.com.sofka.persona.mappers.GenericMapper;
import co.com.sofka.persona.persistence.entities.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class AbstractService<E extends AbstractEntity<K>, D, K> implements GenericService<D,K> {

    protected JpaRepository<E,K> repository;
    protected GenericMapper<D,E, K> mapper;
    protected String notFoundMessage;



    protected AbstractService(JpaRepository<E, K> repository, GenericMapper<D, E, K> mapper, String notFoundMessage) {
        this.repository = repository;
        this.mapper = mapper;
        this.notFoundMessage = notFoundMessage;
    }


    @Override
    public D save(D dto) {
        var entitie = this.mapper.toEntity(dto);
        this.repository.saveAndFlush(entitie);
        return this.mapper.toDto(entitie);
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
        var entitie = this.mapper.toEntity(dto);
        entitie.setId(id);
        return this.mapper.toDto(this.repository.saveAndFlush(entitie));
    }
}
