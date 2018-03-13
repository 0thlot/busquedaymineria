import es.uam.eps.bmi.search.index.impl.SerializedRAMIndexBuilder;

import java.io.IOException;

public class TestHeap {

    public static void main (String a[]) throws IOException {
        String collPath = "collections/docs100k.zip";
        String baseIndexPath = "index/100k";
        new SerializedRAMIndexBuilder().build(collPath, baseIndexPath + "/ramHeap");
    }
}
