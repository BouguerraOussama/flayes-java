package services.users;

import java.util.List;

public interface IService<T> {

    void insert(T t);
    void delete(int id);
    void update(T t);
    List<T>read();

    T readById(int id);
}
