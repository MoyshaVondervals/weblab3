package com.moysha.jsftest.dao;

import com.moysha.jsftest.entity.Points;
import jakarta.enterprise.context.SessionScoped;
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
public class PointsDAO implements Serializable {

    @Getter
    private List<Points> pointsArrayList;

    @PersistenceContext
    EntityManager em;


    @Transactional
    public void savePoints(Points points) {

        em.persist(points);

        updatePoints();
    }
    @Transactional
    public void clear() {

        em.createQuery("delete from Points");

        updatePoints();
    }
    @Transactional
    public void updatePoints(){

        pointsArrayList = em.createQuery("select points from Points points ORDER BY points.id DESC", Points.class).setMaxResults(10).getResultList();
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(pointsArrayList);

            PrimeFaces.current().executeScript("drawDots(" + json + ");");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void dn(){};

}
