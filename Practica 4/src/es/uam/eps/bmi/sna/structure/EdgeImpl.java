package es.uam.eps.bmi.sna.structure;

import java.security.spec.ECParameterSpec;

public class EdgeImpl<U extends Comparable<U>> extends Edge<U> {


    public EdgeImpl(U u, U v) {
        super(u, v);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + first.hashCode();
        result = 17 * result  + second.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof EdgeImpl) && super.compareTo((Edge) obj) == 0;
    }
}
