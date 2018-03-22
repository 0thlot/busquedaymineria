package es.uam.eps.bmi.search.index.structure.impl;

import es.uam.eps.bmi.search.index.structure.EditableDictionary;
import es.uam.eps.bmi.search.index.structure.EditablePostingsList;
import es.uam.eps.bmi.search.index.structure.PostingsList;
import es.uam.eps.bmi.search.index.structure.positional.impl.PositionalPostingListImpl;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PositionalDictionary implements EditableDictionary {

    protected Map<String,PositionalPostingListImpl> termPostings;

    public PositionalDictionary() {
        termPostings = new HashMap<String,PositionalPostingListImpl>();
    }

    public PostingsList getPostings(String term) {
        return termPostings.get(term);
    }

    // We assume docIDs are inserter by order
    @Deprecated
    public void add(String term, int docID) {
        if (termPostings.containsKey(term)) termPostings.get(term).add(docID,-1);
        else termPostings.put(term, new PositionalPostingListImpl (docID,-1));
    }

    public void add(String term, int docID, int pos) {
        if (termPostings.containsKey(term)){
            termPostings.get(term).add(docID, pos);
        }
        else termPostings.put(term, new PositionalPostingListImpl (docID, pos));
    }

    public void add(String term, PostingsList postings) {
        termPostings.put(term, (PositionalPostingListImpl) postings);
    }

    public Collection<String> getAllTerms() {
        return termPostings.keySet();
    }

    @Override
    public long getDocFreq(String term) throws IOException {
        return termPostings.get(term).size();
    }
}
