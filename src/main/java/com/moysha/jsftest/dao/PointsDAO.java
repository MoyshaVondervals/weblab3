package com.moysha.jsftest.dao;


import com.moysha.jsftest.entity.Points;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class PointsDAO {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("CoordinatesPU");

    public void savePoints(Points points) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(points);
        em.getTransaction().commit();
        em.close();
    }

}
