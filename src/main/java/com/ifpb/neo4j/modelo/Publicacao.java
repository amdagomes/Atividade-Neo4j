package com.ifpb.neo4j.modelo;

import java.time.LocalDate;
import java.util.Objects;

public class Publicacao {
    
    private int id;
    private String conteudo;
    private int id_usuario;
    private LocalDate data;

    public Publicacao(int id, String conteudo, int id_usuario, LocalDate data) {
        this.id = id;
        this.conteudo = conteudo;
        this.id_usuario = id_usuario;
        this.data = data;
    }
    
    public Publicacao(String conteudo){
        this.conteudo = conteudo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + this.id;
        hash = 29 * hash + Objects.hashCode(this.conteudo);
        hash = 29 * hash + this.id_usuario;
        hash = 29 * hash + Objects.hashCode(this.data);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Publicacao other = (Publicacao) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.id_usuario != other.id_usuario) {
            return false;
        }
        if (!Objects.equals(this.conteudo, other.conteudo)) {
            return false;
        }
        if (!Objects.equals(this.data, other.data)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Publicacao{" + "id=" + id + ", conteudo=" + conteudo + ", id_usuario=" + id_usuario + ", data=" + data + '}';
    }
    
}
