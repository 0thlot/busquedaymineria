package es.uam.eps.bmi.sna.structure;

import java.security.spec.ECParameterSpec;

public class EdgeImpl<U extends Comparable<U>> extends Edge<U> {


    public EdgeImpl(U u, U v) {
        super(u, v);
    }

    @Override
    public int hashCode() {
        return first.hashCode() + second.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof EdgeImpl) && super.compareTo((Edge) obj) == 0;
    }
}
