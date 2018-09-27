package com.ifpb.neo4j.dao;

public interface DAO<T> {
    
    public boolean inserir(T obj);
    public boolean remover(int id);
    public boolean atualizar(int id, T obj);

}
