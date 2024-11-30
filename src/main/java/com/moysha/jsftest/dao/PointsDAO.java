package com.moysha.jsftest.dao;


import com.moysha.jsftest.entity.Points;
import jakarta.enterprise.context.SessionScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.io.Serializable;

@SessionScoped
public class PointsDAO implements Serializable {
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("d");

    public void savePoints(Points points) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(points);
        em.getTransaction().commit();
        em.close();
    }

}
