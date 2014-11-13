package edu.sjsu.cmpe.cache.client;
import com.google.common.hash.Hashing;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.google.common.hash.*;

public class Client {

    public static void main(String[] args) throws Exception {
        System.out.println("Starting Cache Client...");
        CacheServiceInterface cache1 = new DistributedCacheService( "http://localhost:3000");
        CacheServiceInterface cache2 = new DistributedCacheService( "http://localhost:3001");
        CacheServiceInterface cache3 = new DistributedCacheService( "http://localhost:3002");

        ArrayList<CacheServiceInterface> nodes = new ArrayList();
        nodes.add(cache1);
        nodes.add(cache2);
        nodes.add(cache3);

        CacheServer cache = new CacheServer(Hashing.md5(),10,nodes);
        Map<Integer, String> data = new HashMap<Integer, String>();
        data.put(1,"a");
        data.put(2,"b");
        data.put(3,"c");
        data.put(4,"d");
        data.put(5,"e");
        data.put(6,"f");
        data.put(7,"g");
        data.put(8,"h");
        data.put(9,"i");
        data.put(10,"j");

        for (int i=1;i<=10;i++){
            String value = data.get(i);
//            CacheServiceInterface bucket = (CacheServiceInterface) cache.get(i);
            DistributedCacheService bucket = (DistributedCacheService) cache.get(i);
            bucket.put(i,value);
            System.out.println("Key: " + i + " Value: "+value+"\n");
        }

        for (int j=1;j<=10;j++){
 //           CacheServiceInterface bucket = (CacheServiceInterface) cache.get(j);
            DistributedCacheService bucket = (DistributedCacheService) cache.get(j);
            String value = bucket.get(j);
            System.out.println ("Key: "+ j + " Value: "+ value + " from cache server: " + bucket.getUrl());
        }

        System.out.println("Exiting Cache Client...");

    }

}
