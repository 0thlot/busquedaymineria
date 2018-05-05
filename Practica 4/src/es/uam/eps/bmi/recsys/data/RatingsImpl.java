package es.uam.eps.bmi.recsys.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class RatingsImpl implements Ratings {

    private int nRatings=0;
    private Map<Integer,Set<Integer>> users;
    private Map<Integer,Set<Integer>> items;
    private Map<Integer,Map<Integer,Double>> ratings;

    public RatingsImpl(){
        ratings = new HashMap<>();
        users = new HashMap<>();
        items = new HashMap<>();
    }

    public RatingsImpl(String dataPath, String separator){
        ratings = new HashMap<>();
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
        int user = Integer.valueOf(info[0]);
        int item = Integer.valueOf(info[1]);
        double rating = Double.valueOf(info[2]);
        rate(user,item , rating);
    }

    @Override
    public void rate(int user, int item, Double rating) {

        //Añadimos al double array
        if (!ratings.containsKey(user))
            ratings.put(user, new HashMap<>());
        ratings.get(user).putIfAbsent(item, rating);
        //Añadimos al map de user
        if (!users.containsKey(user)){
            users.put(user,new HashSet<>());
        }
        users.get(user).add(item);
        //Añadimos al map de item
        if (!items.containsKey(item)){
            items.put(item,new HashSet<>());
        }
        items.get(item).add(user);

        nRatings++;
    }

    @Override
    public Double getRating(int user, int item) {

        if(ratings.containsKey(user)){
            return ratings.get(user).get(item);
        }
        return null;
    }

    @Override
    public Set<Integer> getUsers(int item) {
        return (items.get(item)!=null)? new HashSet<>(items.get(item)):new HashSet<>();
    }

    @Override
    public Set<Integer> getItems(int user) {

        return (users.get(user)!=null)?new HashSet<>(users.get(user)):new HashSet<>();
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
        float maximo = 1.0f;
        float minimo = 0.0f;
        Random r = new Random();

        Ratings[] aux = new Ratings[2];
        aux[0] = new RatingsImpl();
        aux[1] = new RatingsImpl();

        this.ratings.forEach((u,v)->{

            v.forEach((i,s)->{
                if((r.nextFloat() * (maximo - minimo) + minimo)<=ratio){
                    aux[0].rate(u,i,s);

                }else{
                    aux[1].rate(u,i,s);
                }
            });
        });

        return aux;
    }
}
