package es.uam.eps.bmi.search.proximity;

import es.uam.eps.bmi.search.AbstractEngine;
import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.index.structure.PostingsListIterator;
import es.uam.eps.bmi.search.index.structure.positional.PositionalPosting;
import es.uam.eps.bmi.search.index.structure.positional.PositionsIterator;
import es.uam.eps.bmi.search.index.structure.positional.impl.PositionalPostingImpl;
import es.uam.eps.bmi.search.ranking.SearchRanking;
import es.uam.eps.bmi.search.ranking.impl.RankingImpl;
import java.io.IOException;
import java.util.Iterator;
import java.util.PriorityQueue;


public class ProximityEngine extends AbstractEngine{
    private  PriorityQueue<PositionalPostingImpl> postingList;
    public ProximityEngine(Index idx) {
        super(idx);
    }

    @Override
    public SearchRanking search(String query, int cutoff) throws IOException {

        boolean literal=false;
        String[] parsed;
        RankingImpl ranking = new RankingImpl(index,cutoff);
        if(query.startsWith("\"") && query.endsWith("\"")){
            literal=true;
            parsed = parse(query.replaceAll("\"",""));
        }else{
            parsed = parse(query);
        }

        PostingsListIterator[] dicIterator = new PostingsListIterator[parsed.length];
        postingList = new PriorityQueue<>();

        for(int i=0;i<parsed.length;i++){
            String t=parsed[i];
            dicIterator[i]=(PostingsListIterator) index.getPostings(t).iterator();
            if(dicIterator[i]!=null && dicIterator[i].hasNext()){
                postingList.add(new PositionalPostingImpl((PositionalPosting)dicIterator[i].next(),i));
            }else{
                return ranking;
            }
        }
        boolean cont=true;
        while(cont){

            if(postingList.stream().allMatch(p -> p.getDocID()==postingList.peek().getDocID())){
                double score =calcularScore(literal);
                if(score>0){
                    ranking.add(postingList.poll().getDocID(),score);
                }
                postingList.clear();
                for(int i=0;i<dicIterator.length;i++){
                    if(dicIterator[i].hasNext()) {
                        postingList.add(new PositionalPostingImpl((PositionalPosting)dicIterator[i].next(),i));
                    }else{
                        cont=false;
                        break;
                    }
                }
            }else{
                PositionalPostingImpl aux = postingList.poll();
                if(dicIterator[aux.getIndexPos()].hasNext()){
                    postingList.add(new PositionalPostingImpl((PositionalPosting)dicIterator[aux.getIndexPos()].next(),aux.getIndexPos()));
                }else{
                    cont=false;
                }
            }
        }
        return ranking;
    }

    private double calcularScore(boolean literal){
        double score=0;

        PositionsIterator[] iterators = new PositionsIterator[postingList.size()];
        int[] positions = new int[postingList.size()];
        int b=Integer.MIN_VALUE;
        int i = 0;
        for(PositionalPostingImpl aux:postingList){
            iterators[i]=aux.iterator();
            if(iterators[i].hasNext()){
                positions[i]=iterators[i].next();
                b=Integer.max(b,positions[i]);
            }else{
                return 0;
            }
            i++;
        }

        int a;

        while (b!=Integer.MAX_VALUE) {
            i = 0;
            for (int j = 0; j < iterators.length; j++) {

                positions[j] = iterators[j].nextBefore(b);

                if (positions[j] < positions[i]) i = j;
            }

            a = positions[i];
            score += (literal && ((b - a + 1) != postingList.size())) ? 0 : ((double) 1 / (b - a - postingList.size() + 2));

            b = iterators[i].nextAfter(a);
            positions[i]=b;
        }
        return score;
    }

}
