package services.users;

import java.sql.SQLException;
import java.util.List;

public interface IService<T> {

    void insert(T t);
    void delete(int id);
    void update(T t);
    List<T>read();
    boolean emailExists(String email);

    T readById(int id);
    String findResetTokenByEmail(String email) throws SQLException;
}
