package com.ifpb.neo4j.view;

import com.ifpb.neo4j.dao.PublicacaoDAO;
import com.ifpb.neo4j.dao.UsuarioDAO;
import com.ifpb.neo4j.modelo.Publicacao;
import com.ifpb.neo4j.modelo.Usuario;
import java.time.LocalDate;
import java.util.List;
import org.neo4j.driver.v1.Record;

public class App {
    
    public static void main (String[] args){
        
        UsuarioDAO userDao = new UsuarioDAO();
        
        //userDao.inserir(new Usuario(1, "Cristine", "cristine@gmail.com", "minhasenha", "Me chamo Cristine"));
        //userDao.inserir(new Usuario(2, "Carlos", "carlos@gmail.com", "minhasenha", "Me chamo Carlos"));
        //userDao.inserir(new Usuario(3, "Ana", "ana@gmail.com", "minhasenha", "Me chamo Ana"));
        //userDao.inserir(new Usuario(4, "Vitoria", "vitoria@gmail.com", "minhasenha", "Me chamo Vitoria"));
        //userDao.inserir(new Usuario(5, "Jonas", "jonas@gmail.com", "minhasenha", "Me chamo Jonas"));
        
        //userDao.follow(3, 5);
        //userDao.follow(5, 4);
        //userDao.follow(5, 1);
        //userDao.follow(1, 2);
        //userDao.follow(1, 3);
        
        //userDao.atualizar(5, new Usuario(5, "Victor", "victor@gmail.com", "novasenha", "novaDescrição"));
        
        System.out.println(userDao.listSeguioresDeSeguidores("jonas@gmail.com"));
        
        //userDao.unfollow(1, 3);
        
        //userDao.remover(1);
        
        PublicacaoDAO pubDao = new PublicacaoDAO();
        
        //pubDao.inserir(new Publicacao(1, "Fiz uma publicação", 2, LocalDate.now()));
        //pubDao.inserir(new Publicacao(2, "Fiz outra publicação", 2, LocalDate.now()));
        //pubDao.inserir(new Publicacao(3, "Oláaaa", 4, LocalDate.now()));
        
        //pubDao.remover(1);
        
        //pubDao.atualizar(3, new Publicacao("Testando"));
        
        try {
            userDao.close();
            pubDao.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
}
