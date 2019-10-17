package com.danilonogueira.veiculos.dao;

import com.danilonogueira.veiculos.entity.Carro;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import com.danilonogueira.veiculos.util.ConnectionFactory;

public class CarroDAO {

    private EntityManager em;

    public CarroDAO() {
        this.em = ConnectionFactory.getEntityManager();
    }

    //CREATE
    public boolean incluir(Carro carro) {
        this.em.getTransaction().begin();
        this.em.persist(carro);
        try {
            this.em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //DELETE
    public boolean excluir(Carro carro) {
        this.em.getTransaction().begin();
        this.em.remove(carro);
        try {
            this.em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //UPDATE
    public boolean alterar(Carro carro) {
        this.em.getTransaction().begin();
        this.em.merge(carro);
        try {
            this.em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //READ
    public List<Carro> listar() {
        Query q = this.em.createQuery("SELECT c FROM com.danilonogueira.veiculos.entity.Carro" +  " c");
        return q.getResultList();
    }
}
