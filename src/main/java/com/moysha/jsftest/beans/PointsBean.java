package com.moysha.jsftest.beans;

import com.moysha.jsftest.dao.PointsDAO;
import com.moysha.jsftest.entity.Points;
import com.moysha.jsftest.utils.CheckArea;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
@Named()
@SessionScoped
public class PointsBean implements Serializable {
    @Inject
    PointsDAO dao;

    private double x;
    private double y;
    private double r = 4;

    public void save() {
        Points points = new Points();
        points.setX(x);
        points.setY(y);
        points.setR(r);
        points.setStatus(CheckArea.checkArea(x, y, r));



        dao.savePoints(points);
    }

}
