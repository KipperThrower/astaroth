package pl.kipperthrower.astaroth.dao;

public interface DaoFactory {

    <T> BaseDaoImpl<T> getDao (Class<T> type);

}
