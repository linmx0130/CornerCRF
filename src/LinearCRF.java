import java.util.ArrayList;

/**
 * Created by Mengxiao Lin on 2015/11/6.
 */
public class LinearCRF {
    private ArrayList<IFeatureFunction> f;
    private ArrayList<Double> lambda;
    private ArrayList<TagType> tagList;
    public LinearCRF(){
        f=new ArrayList<>();
        lambda=new ArrayList<>();
        tagList=new ArrayList<>();
    }
    public void addFunction(IFeatureFunction func){
        f.add(func);
        lambda.add(0.0);
    }
    public void addTag(TagType t){
        tagList.add(t);
    }
    public void init(){
        for (int i=0;i<f.size();++i){
            lambda.set(i,1.0);
        }
    }
    public void feedDataToBuildFunction(ArrayList<TagType> tags, ArrayList<WordType> sequence){
        for (int i=0;i<f.size();++i){
            f.get(i).feedData(tags,sequence);
        }
    }
    public int getFunctionSize(){
        return f.size();
    }

    public void training(ArrayList<TagType> tags, ArrayList<WordType> sequence){
        ArrayList<TagType> predictTags=predict(sequence);
        boolean different=false;
        for (int i=0;i<sequence.size();++i){
            if (!predictTags.get(i).equals(tags.get(i))){
                different=true;
                break;
            }
        }
        if (!different) return;
        for (int k=0;k<f.size();++k){
            double delta=getScoreOfFunctionK(tags,sequence,k)-getScoreOfFunctionK(predictTags,sequence,k);
            lambda.set(k,lambda.get(k)+delta);
        }
    }
    public double getScoreOfFunctionK(ArrayList<TagType> tags, ArrayList<WordType> sequence,int k){
        int dataLen=sequence.size();
        int tagCount=tagList.size();
        double score=1;
        for (int i=1;i<dataLen;++i) {
            score+=f.get(k).test(tags.get(i-1),tags.get(i),sequence,i);
        }
        return score;
    }
    public ArrayList<TagType> predict(ArrayList<WordType> sequence){
        int dataLen=sequence.size();
        int tagCount=tagList.size();
        double scores[][]=new double[dataLen][tagList.size()];
        int lastChoice[][]=new int[dataLen][tagCount];
        for (int i=0;i<tagCount;++i){
            scores[0][i]=1;
        }
        for (int i=1;i<dataLen;++i) {
            for (int j = 0; j < tagCount; ++j) {
                for (int jj = 0; jj < tagCount; ++jj) {
                    double tmp = 0;
                    for (int k = 0; k < f.size(); ++k) {
                        tmp += f.get(k).test(tagList.get(jj), tagList.get(j), sequence, i);
                    }
                    tmp+=scores[i-1][jj];
                    if (tmp > scores[i][j]){
                        scores[i][j]=tmp;
                        lastChoice[i][j]=jj;
                    }
                }
            }
        }
        int[] result=new int[dataLen];
        for (int i=0;i<tagCount;++i){
            if (scores[dataLen-1][i]>scores[dataLen-1][result[dataLen-1]]){
                result[dataLen-1]=i;
            }
        }
        for (int i=dataLen-1;i>0;--i){
            result[i-1]=lastChoice[i][result[i]];
        }
        ArrayList<TagType> ret=new ArrayList<>();
        for (int i=0;i<dataLen;++i){
            ret.add(tagList.get(result[i]));
        }
        return ret;
    }
    public TagType getTag(String name){
        for (int i=0;i<tagList.size();++i){
            if (tagList.get(i).getName().equals(name)){
                return tagList.get(i);
            }
        }
        return null;
    }
}
