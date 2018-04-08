package es.uam.eps.bmi.recsys.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class RatingsImpl implements Ratings {

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
            for (String line: Files.readAllLines(Paths.get(dataPath)))
                readLine(line.split(separator));
        } catch(IOException e){
            System.out.print("Error al abrir el fichero de grafos");
            e.printStackTrace();
        }
    }

    /** Evalua una linea del fichero
     * @param info
     */
    private void readLine(String[] info) throws IOException {
        if (info.length != 3) throw new IOException("Linea con formato incorrecto");
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
        return new HashSet<>(items.get(item));
    }

    @Override
    public Set<Integer> getItems(int user) {
        return new HashSet<>(users.get(user));
    }

    @Override
    public Set<Integer> getUsers() {
        return new HashSet<>(users.keySet());
    }

    @Override
    public Set<Integer> getItems() {
        return new HashSet<>(items.keySet());
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
