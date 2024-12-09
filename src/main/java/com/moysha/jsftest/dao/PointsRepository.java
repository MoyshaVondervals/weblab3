package com.moysha.jsftest.dao;

import com.moysha.jsftest.entity.Points;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Getter;
import org.primefaces.PrimeFaces;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.Serializable;
import java.util.List;


@Named
@SessionScoped
public class PointsRepository implements Serializable {

    @Getter
    private List<Points> pointsArrayList;

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("d");


    public void savePoints(Points points) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(points);

//        drawDots(points);
        em.getTransaction().commit();
        em.close();
        updatePoints();

    }

    public void updatePoints(){
        System.err.println("aboba");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        pointsArrayList = em.createQuery("select points from Points points WHERE points.sessionId = :sid ORDER BY points.id DESC", Points.class).setParameter("sid", getSessionId()).setMaxResults(10).getResultList();

        drawDots(pointsArrayList);
        em.getTransaction().commit();
        em.close();

    }
    public String getSessionId() {
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getExternalContext().getSessionId(false);
    }
    public void drawDots(List<Points> pointsArray){
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(pointsArray);
            System.err.println(json);
            PrimeFaces.current().executeScript("parseDots(" + json + ");");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
