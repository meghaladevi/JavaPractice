package designPatterns.Singleton;

import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * Created by shanmm5 on 16-07-2019.
 */
public class Singleton implements Serializable, Cloneable{
    /*
    // Static Holder pattern (Lazy initialization) //

    //Singleton Holder
    static class Holder{
        static final Singleton INSTANCE = new Singleton();
    }

    public static Singleton getInstance(){
        return Holder.INSTANCE;
    }
    */



    //Eager initialization
    //private static Singleton soleSingletonObj = new Singleton();

    //Lazy initialization, initialization done at private Constructor.
    private static volatile Singleton soleSingletonObj = null;

    //Private Constructor
    private Singleton() {
        //----------- Alert!!!!! Someone is accessing Private Constructor through reflection -----------//
        if(soleSingletonObj != null){
            throw new RuntimeException("Cannot create, please use getInstance()");
        }
        System.out.println("Private Constructor-Creating Object...");
    }

    public static Singleton getInstance(){
        if(soleSingletonObj == null){
            //Narrow down the scope of Synchronization, only when SoleInstance is null.
            synchronized (Singleton.class){
                //By the time the thread enters inside this lock, it is possible that
                //another thread would have created the object.
                //Hence, do double checked locking
                if(soleSingletonObj == null){
                    System.out.println("getInstance-Creating Object...");
                    soleSingletonObj = new Singleton();
                }
            }
        }
        return  soleSingletonObj;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * This overrides the derialization and gives back the sole object.
     * @return
     * @throws ObjectStreamException
     */
    private Object readResolve() throws ObjectStreamException {
        System.out.println("readResolve");
        return soleSingletonObj;
    }
}
