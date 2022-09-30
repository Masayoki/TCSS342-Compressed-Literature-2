import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.Set;

public class MyHashTable<K,V> {
    private MyEntry[] myBuckets;
    private int myCap;
    public Set<K> keySet;

    public MyHashTable(int capacity) {
        myBuckets = (MyEntry[]) Array.newInstance(MyEntry.class, capacity);
        keySet = new HashSet<K>();
        myCap = capacity;
    }

    public void put(K searchKey, V newValue) {
        int idx = hash(searchKey);
        MyEntry newEntry = new MyEntry(searchKey, newValue);
        if (!keySet.contains(searchKey)) {
            keySet.add(searchKey);
        }
        if (myBuckets[idx] == null) {
            myBuckets[idx] = newEntry;
        } else {
            while (myBuckets[idx] != null) {
                if (myBuckets[idx].myK.equals(searchKey)) {
                    break;
                }
                if (idx == myBuckets.length - 1) {
                    idx = 0;
                } else {
                    idx = idx + 1;
                }
            }
            myBuckets[idx] = newEntry;
        }
    }

    private class MyEntry {
        K myK;
        V myV;

        public MyEntry(K theK, V theV) {
            myK = theK;
            myV = theV;
        }
    }

    public V get(K searchKey) {
        int idx = hash(searchKey);
        if (myBuckets[idx].myK.equals(searchKey)) {
            return myBuckets[idx].myV;
        } else {
            while (!myBuckets[idx].myK.equals(searchKey)) {
                if (idx == myBuckets.length - 1) {
                    idx = 0;
                } else {
                    idx++;
                }
            }
            return myBuckets[idx].myV;
        }
    }

    public boolean containsKey(K searchKey) {
        return keySet.contains(searchKey);
    }

    public void stats() {
        System.out.println("Number of Entries: " + keySet.size());
        System.out.println("Number of Buckets: " + myCap);
        System.out.println(String.format("Fill percentage: %.6f", (double) keySet.size() / myCap * 100) + "%");
    }

    private int hash(K key) {
        return Math.abs(key.hashCode()) % (myBuckets.length - 1);
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (myBuckets[i] == null) {
            i++;
        }
        sb.append("[");
        sb.append("(" + myBuckets[i].myK.toString() + ", " + myBuckets[i].myV.toString() + ")");
        for (i = i + 1; i < myBuckets.length; i++) {
            if (myBuckets[i] != null) {
                sb.append(", (" + myBuckets[i].myK.toString() + ", " + myBuckets[i].myV.toString() + ")");
            }
        }
        sb.append("]");
        return sb.toString();
    }

}
