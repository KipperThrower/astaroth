package pl.kipperthrower.astaroth.core.dao;

public interface DaoFactory {

    <T> BaseDaoImpl<T> getDao (Class<T> type);

}
