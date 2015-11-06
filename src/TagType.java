/**
 * Created by Mengxiao Lin on 2015/11/6.
 */
public class TagType {
    private String name;
    public TagType(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TagType tagType = (TagType) o;

        return !(name != null ? !name.equals(tagType.name) : tagType.name != null);

    }
}
