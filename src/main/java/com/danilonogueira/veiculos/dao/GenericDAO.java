/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.danilonogueira.veiculos.dao;

import com.danilonogueira.veiculos.util.ConnectionFactory;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author danilo
 */
public class GenericDAO<E> {
    
    
    private Class classe;
    private EntityManager em;
    
    public GenericDAO(){
        this.classe = (Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.em = ConnectionFactory.getEntityManager();
    }
    
    //CREATE
    public boolean incluir(E entidade) {
        this.em.getTransaction().begin();
        this.em.persist(entidade);
        try {
            this.em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    //READ
    public List<E> listar() {
        Query q = this.em.createQuery("SELECT c FROM " + this.classe.getName() +  " c");
        return q.getResultList();
    }
    
    //UPDATE
    public boolean alterar(E entidade) {
        this.em.getTransaction().begin();
        this.em.merge(entidade);
        try {
            this.em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    //DELETE
    public boolean excluir(E entidade) {
        this.em.getTransaction().begin();
        this.em.remove(entidade);
        try {
            this.em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
