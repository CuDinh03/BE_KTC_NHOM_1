package ktc.nhom1ktc.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IService <T>{
    T getByID(UUID id);
    T create( T t);

    T update (UUID uuid, T t);

    void delete(UUID id);

    List<T> getAll();

    Page<T> getAllPageable(Pageable pageable);
}
