package com.moysha.jsftest.dao;

import com.moysha.jsftest.entity.Points;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.persistence.*;
import org.primefaces.PrimeFaces;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class PointsDAO implements Serializable {
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("d");
    public void savePoints(Points points) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(points);
        em.getTransaction().commit();
        em.close();
        updatePoints();
    }

    public void clear() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.clear();
        em.getTransaction().commit();
        em.close();
        updatePoints();
    }
    public void updatePoints(){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Points> pointsArrayList = em.createQuery("select points from Points points ORDER BY points.id DESC", Points.class).setMaxResults(10).getResultList();
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(pointsArrayList);

            PrimeFaces.current().executeScript("drawDots(" + json + ");");
        }catch (Exception e){
            e.printStackTrace();
        }
        em.getTransaction().commit();
        em.close();
    }

}
