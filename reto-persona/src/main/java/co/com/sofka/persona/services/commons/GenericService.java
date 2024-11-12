package co.com.sofka.persona.services.commons;

import java.util.List;

public interface GenericService <D, K> {

    D save(D dto);
    List<D> getAll();
    D getById(K id);
    void delete( K id);
    D update(D dto, K id);


}
