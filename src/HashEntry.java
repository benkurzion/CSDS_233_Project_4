public class HashEntry {

    private String key;
    private int value;

    public HashEntry(String key, int value){
        this.key = key;
        this.value = value;
    }

    public String getKey(){
        return this.key;
    }

    public int getValue(){
        return this.value;
    }

    public void setValue(int value){
        this.value = value;
    }
}
