package com.ifpb.neo4j.dao;

import com.ifpb.neo4j.connection.DriverFactory;
import com.ifpb.neo4j.modelo.Usuario;
import java.util.ArrayList;
import java.util.List;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import static org.neo4j.driver.v1.Values.parameters;
import org.neo4j.driver.v1.exceptions.ClientException;

public class UsuarioDAO implements DAO<Usuario>, AutoCloseable {

    private Driver driver;
    private Session session;

    public UsuarioDAO() {
        driver = new DriverFactory().getDriver();
        session = driver.session();
    }

    @Override
    public boolean inserir(Usuario usuario) {
        int cont = 0;
        try (Transaction tx = session.beginTransaction()) {
            StatementResult result = tx.run("CREATE(u:Usuario{id:$id, nome:$nome, email:$email, senha:$senha, "
                    + "descricao:$descricao}) return u",
                    parameters("id", usuario.getId(), "nome", usuario.getNome(), "email", usuario.getEmail(),
                            "senha", usuario.getSenha(), "descricao", usuario.getDescricao()));
            cont = result.summary().counters().nodesCreated();
            tx.success();
        }
        return cont > 0;
    }

    @Override
    public boolean remover(int id) {
        int cont = 0;
        try(Transaction tx = session.beginTransaction()){
            StatementResult result = tx.run("MATCH (u:Usuario{id:$id})-[r:Segue]-() DELETE u, r",
                                     parameters("id", id));
            
            cont = result.summary().counters().nodesDeleted();
            tx.success();
        }
        
        return cont > 0;
    }

    @Override
    public boolean atualizar(int id, Usuario usuario) {
        int cont = 0;
        try (Transaction tx = session.beginTransaction()){
            StatementResult result = tx.run("MATCH (u:Usuario) WHERE u.id = $id SET u.nome = $nome, "
                    + "u.email = $email, u.senha = $senha , u.descricao = $descricao RETURN u", 
                    parameters("id", id, "nome", usuario.getNome(), "email", usuario.getEmail(),
                                "senha", usuario.getSenha(), "descricao", usuario.getDescricao()));
            
            cont = result.summary().counters().propertiesSet();
            tx.success();
        }
        return cont > 0;
    }
    
    public boolean follow(int seguidor, int usuario){
        int cont = 0;
        try(Transaction tx = session.beginTransaction()){
            StatementResult result = tx.run("MATCH (s:Usuario), (u:Usuario) WHERE s.id = $seguidor AND u.id = $usuario "
                    + "CREATE (s)-[r:Segue]->(u) RETURN s, u, r", parameters("seguidor", seguidor, "usuario", usuario));
            
            cont = result.summary().counters().nodesCreated();
            tx.success();
        }
        
        return cont > 0;
    }
    
    public boolean unfollow(int seguidor, int usuario){
        int cont;
        try (Transaction tx = session.beginTransaction()) {
            StatementResult result = tx.run( "MATCH (:Usuario{id:$seguidor})-[r:Segue]->(:Usuario{id:$usuario}) DELETE r",
                    parameters("seguidor", seguidor,
                            "usuario", usuario));

            cont = result.summary().counters().nodesCreated();
            tx.success();
        }catch (ClientException ex){
            return false;
        }
        return cont > 0;
    }

    public List listSeguioresDeSeguidores(String email) {
       List lista = new ArrayList();
       try(Transaction tx = session.beginTransaction()){
           StatementResult result = tx.run("MATCH (:Usuario{email:$email})-[:Segue]->(:Usuario)-[:Segue]->(users:Usuario) "
                                            + "RETURN users.nome", parameters("email", email));
           
           while(result.hasNext()){
               Record record = result.next();
               lista.add(record.get("users.nome").asString());
           }
           tx.success();
       }
       return lista;
    }

    public Usuario buscar(String email) {
        try (Transaction tx = session.beginTransaction()) {
            StatementResult result = tx.run("MATCH (u:Usuario) WHERE u.email = $email return u.email, "
                    + "u.nome, u.descricao RETURN u", parameters("email", email));

            if (result.hasNext()) {
                Record record = result.next();

                String nome = record.get("u.nome").asString();
                String descricao = record.get("u.descricao").asString();

                return new Usuario(nome, email, descricao);

            } else {
                return null;
            }
        }
    }

    @Override
    public void close() throws Exception {
        session.close();
        driver.close();
    }

}
