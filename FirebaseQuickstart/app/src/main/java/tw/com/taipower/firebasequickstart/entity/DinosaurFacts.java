package tw.com.taipower.firebasequickstart.entity;

/**
 * Created by new on 2016/4/26.
 */
public class DinosaurFacts {
    double height;
    double length;
    double weight;

    public DinosaurFacts() {
        // empty default constructor, necessary for Firebase to be able to deserialize blog posts
    }

    public double getHeight() {
        return height;
    }

    public double getLength() {
        return length;
    }

    public double getWeight() {
        return weight;
    }
}
