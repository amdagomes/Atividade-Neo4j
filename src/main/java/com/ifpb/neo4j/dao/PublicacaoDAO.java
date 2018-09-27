package com.ifpb.neo4j.dao;

import com.ifpb.neo4j.connection.DriverFactory;
import com.ifpb.neo4j.modelo.Publicacao;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import static org.neo4j.driver.v1.Values.parameters;

public class PublicacaoDAO implements DAO<Publicacao>, AutoCloseable {
    
    private Driver driver;
    private Session session;
    
    public PublicacaoDAO(){
        driver = new DriverFactory().getDriver();
        session = driver.session();
    }

    @Override
    public boolean inserir(Publicacao obj) {
        int cont = 0;
        try(Transaction tx = session.beginTransaction()){
            StatementResult result = tx.run("MATCH (u:Usuario{id:$id_user})"
                        + " CREATE (u)-[:Publicou{data:$data}]->(:Publicacao{id:$id, conteudo:$conteudo, "
                        + "id_user:u.id})", parameters("id_user", obj.getId_usuario(), "data", obj.getData(),
                          "id", obj.getId(), "conteudo", obj.getConteudo()));
            
            cont = result.summary().counters().nodesCreated();
            tx.success();
        }
        return cont > 0;
    }

    @Override
    public boolean remover(int id) {
        int cont = 0;
        try(Transaction tx = session.beginTransaction()){
            StatementResult result = tx.run("MATCH (p:Publicacao{id:$id})-[r:Publicou]-() delete p, r", 
                                            parameters("id", id));
            
            cont= result.summary().counters().nodesDeleted();
            tx.success();
        }
        return cont > 0;
    }

    @Override
    public boolean atualizar(int id, Publicacao obj) {
       int cont = 0;
       try(Transaction tx = session.beginTransaction()){
           StatementResult result = tx.run("MATCH (p:Publicacao{id:$id}) SET p.conteudo = $conteudo",
                   parameters("id", id, "conteudo", obj.getConteudo()));
           
           cont = result.summary().counters().propertiesSet();
           tx.success();
       }
       return cont > 0;
    }
    
    @Override
    public void close() throws Exception {
        session.close();
        driver.close();
    }
    
}
