import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Mengxiao Lin on 2015/11/6.
 */
public class Main {
    static BufferedReader fin;
    static LinearCRF crf;
    static ArrayList<ArrayList<TagType>> tagsData;
    static ArrayList<ArrayList<WordType>> seqData;
    public static void getData() throws IOException {
        String line=null;
        while (true) {
            ArrayList<TagType> tags=new ArrayList<>();
            ArrayList<WordType> sequence=new ArrayList<>();
            tags.add(crf.getTag("#"));
            sequence.add(new WordType("#@"));
            while (true) {
                line = fin.readLine();
                if (line ==null) break;
                if (line.equals("")) break;
                String d[] = line.split(" ");
                sequence.add(new WordType(d[0]));
                tags.add(crf.getTag(d[1]));
            }
            if (line==null) break;
            tagsData.add(tags);
            seqData.add(sequence);
        }
    }
    public static void main(String args[]) throws IOException {
        crf=new LinearCRF();
        IFeatureFunction f1=new IFeatureFunction() {
            HashSet<NTuple> data=new HashSet<>();
            @Override
            public void feedData(ArrayList<TagType> tags, ArrayList<WordType> sequence) {
                int dataLen=sequence.size();
                for (int i=1;i<dataLen-2;++i){
                    NTuple t=new NTuple(4);
                    t.put(0,tags.get(i-1));
                    t.put(1,tags.get(i));
                    t.put(2,sequence.get(i));
                    t.put(3,sequence.get(i+1));
                    try {
                        data.add(t);
                    } catch (NullPointerException e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public double test(TagType tagBefore, TagType tagNow, ArrayList<WordType> sequence, int pos) {
                NTuple t=new NTuple(4);
                t.put(0,tagBefore);
                t.put(1,tagNow);
                t.put(2,sequence.get(pos));
                if (sequence.size()<=pos+1){
                    return 0;
                }
                t.put(3,sequence.get(pos+1));
                if (data.contains(t)){
                    return 1;
                }
                return 0;
            }
        };
        IFeatureFunction f2=new IFeatureFunction() {
            HashSet<NTuple> data=new HashSet<>();
            @Override
            public void feedData(ArrayList<TagType> tags, ArrayList<WordType> sequence) {
                int dataLen=sequence.size();
                for (int i=1;i<dataLen-1;++i){
                    NTuple t=new NTuple(4);
                    t.put(0,tags.get(i-1));
                    t.put(1,tags.get(i));
                    t.put(2,sequence.get(i));
                    t.put(3,sequence.get(i-1));
                    data.add(t);
                }
            }

            @Override
            public double test(TagType tagBefore, TagType tagNow, ArrayList<WordType> sequence, int pos) {
                NTuple t=new NTuple(4);
                t.put(0,tagBefore);
                t.put(1,tagNow);
                t.put(2,sequence.get(pos));
                if (pos ==0) return 0;
                t.put(3,sequence.get(pos-1));
                if (data.contains(t)){
                    return 1;
                }
                return 0;
            }
        };
        tagsData=new ArrayList<>();
        seqData=new ArrayList<>();
        crf.addTag(new TagType("#"));
        crf.addTag(new TagType("S"));
        crf.addTag(new TagType("B"));
        crf.addTag(new TagType("I"));
        crf.addTag(new TagType("E"));
        crf.addFunction(f1);
        crf.addFunction(f2);
        crf.init();
        fin=new BufferedReader(new InputStreamReader(new FileInputStream("D:/train.utf8"),"utf-8"));
        getData();
        fin.close();
        int dataCount=tagsData.size();
        for (int i=0;i<dataCount;++i){
            crf.feedDataToBuildFunction(tagsData.get(i),seqData.get(i));
        }
        for (int times=0;times<100;++times) {
            System.out.println("Times="+times);
            for (int i = 0; i < dataCount; ++i) {
                crf.training(tagsData.get(i),seqData.get(i));
            }
            for (int i=0;i<10;++i) {
                int t = (int) (Math.random() * seqData.size());
                ArrayList<TagType> tagsPredict=crf.predict(seqData.get(t));
                for (int k=0;k<tagsPredict.size();++k){
                    System.out.print(seqData.get(t).get(k).getContent());
                }
                System.out.println();
                for (int k=0;k<tagsPredict.size();++k){
                    System.out.print(tagsPredict.get(k).getName());
                }
                System.out.println();
                for (int k=0;k<tagsPredict.size();++k){
                    System.out.print(tagsData.get(t).get(k).getName());
                }
                System.out.println();
            }
            System.out.println();
        }

    }
}
