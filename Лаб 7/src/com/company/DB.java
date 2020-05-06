package com.company;

import java.sql.*;

public class DB{
    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException{
        String connectionString = "jdbc:mysql://localhost/world?useUnicode=true&serverTimezone=UTC&useSSL=true&verifyServerCertificate=false";

        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, "root", "123456789");
        return dbConnection;
    }

    public void addObj(Animal animal)  {
        String insert = "INSERT INTO objs (X,Y,ID,type) VALUES (?,?,?,?)";
        String type = null;
        if(animal instanceof Cat) type = "cat";
        else if(animal instanceof Dog) type = "dog";
        PreparedStatement prST = null;
        try {
            prST = getDbConnection().prepareStatement(insert);
            prST.setString(1, String.valueOf(animal.getX()));
            prST.setString(2, String.valueOf(animal.getY()));
            prST.setString(3, String.valueOf(animal.getID()));
            prST.setString(4, type);

            prST.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addCat(Animal cat) {
        String insert = "INSERT INTO objs (X,Y,ID,type) VALUES (?,?,?,?)";
        PreparedStatement prST = null;
        try {
            prST = getDbConnection().prepareStatement(insert);
            prST.setString(1, String.valueOf(cat.getX()));
            prST.setString(2, String.valueOf(cat.getY()));
            prST.setString(3, String.valueOf(cat.getID()));
            prST.setString(4, "cat");

            prST.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addDog(Animal dog) {
        String insert = "INSERT INTO objs (X,Y,ID,type) VALUES (?,?,?,?)";
        PreparedStatement prST = null;
        try {
            prST = getDbConnection().prepareStatement(insert);
            prST.setString(1, String.valueOf(dog.getX()));
            prST.setString(2, String.valueOf(dog.getY()));
            prST.setString(3, String.valueOf(dog.getID()));
            prST.setString(4, "dog");

            prST.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getObj(boolean isCat) {
        ResultSet resSet = null;

        String select = "SELECT * FROM objs WHERE type=?";

        PreparedStatement prST = null;
        try {
            prST = getDbConnection().prepareStatement(select);
            prST.setString(1, isCat ? "cat" : "dog");
            resSet = prST.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }

    public ResultSet getObjs() {
        ResultSet resSet = null;

        String select = "SELECT * FROM objs";

        PreparedStatement prST = null;
        try {
            prST = getDbConnection().prepareStatement(select);
            resSet = prST.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }
}
