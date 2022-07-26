import java.util.Locale;

public class HashTable {

    private HashEntry[] hashEntries;
    private int numItems = 0;

    public HashTable(){
        hashEntries = new HashEntry[50];
    }

    public HashTable(int size){
        hashEntries = new HashEntry[size];
    }


    public void put (String key, int value){
        //System.out.println("In put(1) for " + key);
        if(get(key) == -1) {
            //System.out.println(key + " is not in the list");
            numItems++;
            if ((numItems * 2 >= hashEntries.length)) {//resize the array when load factor = .5
               //System.out.println("Rehashing");
                HashEntry[] temp = new HashEntry[hashEntries.length * 2 + 1];
                for (int i = 0; i < hashEntries.length; i++) {
                    if (hashEntries[i] != null)
                        temp[linearProbing(hashEntries[i], temp)] = hashEntries[i];
                }
                hashEntries = temp;
            }//linear probe for the place to put the new entry.
            //System.out.println("Using Linear Probing to insert " + key);
            hashEntries[linearProbing(new HashEntry(key, value), hashEntries)] = new HashEntry(key, value);
        }
    }


    public void put(String key, int value, int hashCode){
        //System.out.println("In put (2) for " + key);
        if(get(key) == -1) {
            //System.out.println(key + " is not in the list");
            numItems++;
            if ((numItems * 2 >= hashEntries.length)) {//resize the array
                //System.out.println("Rehashing");
                HashEntry[] temp = new HashEntry[hashEntries.length * 2 + 1];
                for (int i = 0; i < hashEntries.length; i++) {
                    temp[linearProbing(hashEntries[i], temp)] = hashEntries[i];
                }
                hashEntries = temp;
            }//linear probe from given hash value for the place to put the new entry.
            int increment = 0;
            while (hashEntries[(hashCode + increment) % hashEntries.length] != null) {
                //System.out.println("With given hash for " + key + ", collision occured at " + hashCode % hashEntries.length);
                increment++;
            }
            //System.out.println("Finished linear probing from given hash code " + increment +  " spaces down");
            hashEntries[(hashCode + increment) % hashEntries.length] = new HashEntry(key, value);
        }
    }

    public void update (String key, int value){//updates the single instance of the same string
       //System.out.println("In update for " + key);
        if(get(key) == -1) {
           // System.out.println(key + " is not in the list and will be inserted using put (1)");
            put(key, value);
        }else{
            //System.out.println(key + " is in the list and will be updated");
            int temp;
            for (int i = 0; i < hashEntries.length; i++){
                temp = Math.abs(key.hashCode() + i);
                if(hashEntries[temp % hashEntries.length] != null){
                    if(hashEntries[temp % hashEntries.length].getKey().hashCode() == key.hashCode()){
                        hashEntries[temp % hashEntries.length] = new HashEntry(key, value);
                        return;//string has been found and updated. break loop
                    }
                }
            }
        }

    }

    public int get (String key){
        //System.out.println("In get (1) for " + key);
        int temp;
        //System.out.println(key + "'s absolute val of hashcode = " + Math.abs(key.hashCode()));
        //System.out.println("Mod that with " + hashEntries.length + " = " + (Math.abs(key.hashCode()) % hashEntries.length));
        for(int i = 0; i < hashEntries.length; i++){
            temp = Math.abs(key.hashCode()) + i;
            if(hashEntries[temp % hashEntries.length] == null){
               // System.out.println("A null has been reached " + i + " spaces away from the start. " + key + " is not in list");
                return -1;
            }
            //System.out.println("hashEntries[temp % hashEntries.length] = " + hashEntries[temp % hashEntries.length]);
            if(hashEntries[temp % hashEntries.length].getKey().equals(key)){
                //System.out.println(key +" has been found in the list");
                return hashEntries[temp % hashEntries.length].getValue();
            }
           // System.out.println(key + " not found at " + (temp % hashEntries.length) + " (non-null)");
        }
        return -1;
    }

    public int get (String key, int hashCode){
        //System.out.println("In get (2) for " + key);
        int temp;
        //System.out.println(key + "'s absolute val of hashcode = " + Math.abs(hashCode));
        //System.out.println("Mod that with " + hashEntries.length + " = " + (Math.abs(hashCode) % hashEntries.length));
        for(int i = 0; i < hashEntries.length; i++) {
            temp = Math.abs(hashCode) + i;
            if (hashEntries[temp % hashEntries.length] == null){
                //System.out.println("A null has been reached " + i + " spaces away from the start");
                return -1;
            }
           if ( hashEntries[temp % hashEntries.length].getKey().equals(key))
                return hashEntries[temp % hashEntries.length].getValue();
        }
        return -1;
    }

    private int linearProbing(HashEntry obj, HashEntry[] arr){
       // System.out.println("In linear probing for " + obj.getKey());
        int increment = 0;
        int temp = Math.abs(obj.getKey().hashCode());
        while(arr[(temp + increment) % arr.length] != null){
            //System.out.println("Found collision at " + (temp + increment) % arr.length);
            increment++;
        }
       // System.out.println("Found spot for " + obj.getKey() + " " + increment + " spots down in the list");
        return (temp + increment) % arr.length;
    }

    public String print(){
        StringBuilder obj = new StringBuilder();
        for(int i = 0; i < hashEntries.length; i++){
            if(hashEntries[i] != null){
                obj.append(hashEntries[i].getKey() + " (" + hashEntries[i].getValue() + ") ");
            }
        }
        return obj.toString();
    }

}
