import java.util.Arrays;

/**
 * Created by Mengxiao Lin on 2015/11/6.
 */
public class NTuple {
    private int n;
    private Object objects[];
    public NTuple(int n){
        this.n=n;
        this.objects=new Object[n];
    }
    public void put(int pos,Object o){
        objects[pos]=o;
    }
    public Object get(int pos){
        return objects[pos];
    }
    @Override
    public int hashCode() {
        int ret=0;
        for (int i=0;i<n;++i){
            ret=ret*233+objects[i].hashCode();
        }
        return ret;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NTuple nTuple = (NTuple) o;

        if (n != nTuple.n) return false;
        for (int i=0;i<n;++i){
            if (!objects[i].equals(nTuple.objects[i])){
                return false;
            }
        }
        return true;

    }
}
