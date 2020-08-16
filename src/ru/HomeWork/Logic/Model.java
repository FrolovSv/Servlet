package ru.HomeWork.Logic;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Model implements Serializable {
    private static final Model instance = new Model();

    private final Map<Integer,User> model;

    public static Model getInstance(){
        return instance;
    }

    public Model() {
        model = new HashMap<>();

        model.put(1, new User("Сергей","Фролов",40000.0));
        model.put(2, new User("Мария","Тезина",80000.0));
        model.put(3, new User("Алиса","Фролова",100000.0));
    }

    public void addUser(User user, int id){
        model.put(id,user);
    }

    public Map<Integer, User> getFromList() {
        return model;
    }

    public boolean deleteUser(int id){
        try {
            if (id>0 && id <= model.size()) {
                model.remove(id);
                return true;
            }else if (id==0){
                model.clear();
                return true;
            }else
                return false;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void changeUser(int id, User user){
        model.put(id,user);
    }
}
