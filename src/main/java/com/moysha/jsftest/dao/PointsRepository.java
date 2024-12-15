package com.moysha.jsftest.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moysha.jsftest.entity.Points;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import lombok.Getter;
import org.primefaces.PrimeFaces;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Named
@SessionScoped
public class PointsRepository implements Serializable {

    private List<Points> pointsArrayList;

    public void savePoints(Points points) throws SQLException {
        try (PreparedStatement addObjStatement = DataBaseManager.getConnection().prepareStatement(
                "INSERT INTO points (x, y, r, status, sessionid) VALUES (?, ?, ?, ?, ?)")) {
            addObjStatement.setDouble(1, points.getX());
            addObjStatement.setDouble(2, points.getY());
            addObjStatement.setDouble(3, points.getR());
            addObjStatement.setBoolean(4, points.isStatus());
            addObjStatement.setString(5, points.getSessionId());
            addObjStatement.executeUpdate();
            updatePoints();
        }
    }

    public void updatePoints() {
        try {
            pointsArrayList = loadPointsFromDb();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        drawDots(pointsArrayList);
    }

    private List<Points> loadPointsFromDb() throws SQLException {
        List<Points> list = new ArrayList<>();
        String sql = "SELECT x, y, r, status, sessionid FROM points WHERE sessionid = ? ORDER BY id DESC LIMIT 10";
        try (PreparedStatement ps = DataBaseManager.getConnection().prepareStatement(sql)) {
            ps.setString(1, getSessionId());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Points p = new Points();
                    p.setX(rs.getDouble("x"));
                    p.setY(rs.getDouble("y"));
                    p.setR(rs.getDouble("r"));
                    p.setStatus(rs.getBoolean("status"));

                    list.add(p);
                }
            }
        }
        return list;
    }

    public String getSessionId() {
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getExternalContext().getSessionId(false);
    }

    public void drawDots(List<Points> pointsArray) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(pointsArray);
            System.out.println(json);
             PrimeFaces.current().executeScript("parseDots(" + json + ");");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
