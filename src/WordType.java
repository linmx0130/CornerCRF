/**
 * Created by Mengxiao Lin on 2015/11/6.
 */
public class WordType {
    private String content;
    public WordType(String content){
        this.content=content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public int hashCode() {
        return content.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WordType wordType = (WordType) o;

        return !(content != null ? !content.equals(wordType.content) : wordType.content != null);

    }
}
