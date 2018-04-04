package es.uam.eps.bmi.recsys.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class RatingsImpl implements Ratings {

    private String dataPath;
    private String separator;
    private int nRatings=0;
    private Map<Integer,Set<Integer>> users;
    private Map<Integer,Set<Integer>> items;
    private List<List<Double>> ratings;

    public RatingsImpl(String dataPath, String separator){
        ratings = new ArrayList<>();
        users = new HashMap<>();
        items = new HashMap<>();

        //Leer fichero
        try {
            for (String line: Files.readAllLines(Paths.get(dataPath))){
                readLine(line.split("[ \t]"));
            }
        } catch(IOException e){
            System.out.print("Error al abrir el fichero de grafos");
            e.printStackTrace();
        }


        //Iterar lineas
        //En cada linea llamar a rate
    }

    /**
     * @param info
     */
    private void readLine(String[] info){
       rate(Integer.getInteger(info[0]), Integer.getInteger(info[1]), Double.valueOf(info[2]));
    }

    @Override
    public void rate(int user, int item, Double rating) {

        //Añadimos al double array
        if (!ratings.contains(user))
            ratings.set(user, new ArrayList<>());
        ratings.get(user).set(item, rating);
        //Añadimos al map de user
        if (!users.containsKey(user)){
            Set<Integer> set = new HashSet<>();
            users.put(user,set);
        }
        users.get(user).add(item);
        //Añadimos al map de item
        if (!items.containsKey(user)){
            Set<Integer> seti = new HashSet<>();
            items.put(user,seti);
        }
        items.get(item).add(user);

        nRatings++;
    }

    @Override
    public Double getRating(int user, int item) {
        try{
            return ratings.get(user).get(item);
        }catch (NullPointerException e){
            return 0.0;
        }
    }

    @Override
    public Set<Integer> getUsers(int item) {
        return items.get(item);
    }

    @Override
    public Set<Integer> getItems(int user) {
        return users.get(user);
    }

    @Override
    public Set<Integer> getUsers() {
        return users.keySet();
    }

    @Override
    public Set<Integer> getItems() {
        return items.keySet();
    }

    @Override
    public int nRatings() {
        return nRatings;
    }

    @Override
    public Ratings[] randomSplit(double ratio) {
        return new Ratings[0];
    }
}
