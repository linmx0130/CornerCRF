import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mengxiao Lin on 2015/11/6.
 */
public interface IFeatureFunction {
    void feedData(ArrayList<TagType> tags, ArrayList<WordType> sequence);
    double test(TagType tagBefore, TagType tagNow, ArrayList<WordType> sequence, int pos);
}
