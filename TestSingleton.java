package designPatterns.Singleton;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by shanmm5 on 16-07-2019.
 */
public class TestSingleton {
    public static void main(String args[]){
        Singleton s1 = Singleton.getInstance();
        Singleton s2 = Singleton.getInstance();

        print("s1" , s1);
        print("s2" , s2);


        System.out.println("###### Reflection ######");
        //Reflection
        try{
            Class clazz = Class.forName("designPatterns.Singleton.Singleton");
            Constructor<Singleton> singletonConstructor = clazz.getDeclaredConstructor();
            singletonConstructor.setAccessible(true);
            Singleton sReflection = singletonConstructor.newInstance();
            //Hashcode is different. We have violated Singleton design.
            print("sReflection" , sReflection);
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (NoSuchMethodException e){
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            System.out.println("--- <Reflection> Could not create Object");
            e.printStackTrace();
        }

        //Serialization
        System.out.println("###### Serialization and DeSerialization ######");
        try {
            ObjectOutputStream oos = new ObjectOutputStream
                    (new FileOutputStream("C:\\CFD\\Meghalas Data\\Meghala_Java_Workspace\\SerializedObject\\S2.ser"));
            oos.writeObject(s2);

            ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream("C:\\CFD\\Meghalas Data\\Meghala_Java_Workspace\\SerializedObject\\S2.ser"));
            Singleton sDeserialized = (Singleton)ois.readObject();
            print("sDeserialized" , sDeserialized);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Clone
        try{
            Singleton sCloned = (Singleton)s2.clone();
            print("sCloned" , sCloned);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Threads
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(TestSingleton:: useSingleton);
        executorService.submit(TestSingleton:: useSingleton);
        executorService.shutdown();
    }


    static void useSingleton() {
        Singleton singleton = Singleton.getInstance();
        print("singleton" , singleton);
    }

    static void print(String name, Singleton object){
        System.out.println(String.format("Öbject %s, Hashcode: %d" ,name,object.hashCode()));
    }
}
