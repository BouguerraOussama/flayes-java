package services.offers;



import java.sql.SQLException;
import java.util.List;

public interface IService <T>{
     int create(T t) throws SQLException;
    void update(T t) throws SQLException;
    void delete(int id) throws SQLException;
    List<T> read() throws SQLException;
}
