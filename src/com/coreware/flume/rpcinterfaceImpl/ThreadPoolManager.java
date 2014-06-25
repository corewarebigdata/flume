package com.coreware.flume.rpcinterfaceImpl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.flume.Event;

public class ThreadPoolManager {  
    private static ThreadPoolManager tpm = new ThreadPoolManager();
    
    private final static int CORE_POOL_SIZE = 1;  
    private final static int MAX_POOL_SIZE = 1;  
    private final static int KEEP_ALIVE_TIME = 100;  
    private final static int WORK_QUEUE_SIZE = 100;  
    
    Queue<Object> msgQueue = new LinkedList();  

    final Runnable accessBufferThread = new Runnable() {
        public void run() {  
        	List<Event> eventList = new ArrayList<Event>();
                synchronized(eventList){
                	if(Quene.incache.size()>0){
    	            	synchronized(Quene.incache){
    	            		Quene.incache.drainTo(eventList,Quene.incache.size()/2);
    	            	}
                	}
                }  
                // System.out.println("还剩下的输出队列长度\t"+Quene.incache.size());
                // System.out.println("每次取出多少长度\t"+eventList.size());
                Runnable task = new MessageThread( eventList );
                threadPool.execute( task );  
        }  
   };
 
    final RejectedExecutionHandler handler = new RejectedExecutionHandler()  
    {  
        public void rejectedExecution( Runnable r, ThreadPoolExecutor executor )  
        {  
            msgQueue.offer((( MessageThread ) r ).getEventList());  
        }
    };
 
    final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(  
            CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.MILLISECONDS,  
            new ArrayBlockingQueue( WORK_QUEUE_SIZE ), this.handler );  
    
    final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
  
    final ScheduledFuture taskHandler = scheduler.scheduleAtFixedRate(  
            accessBufferThread, 0, 300, TimeUnit.MILLISECONDS );
    
 
    public static ThreadPoolManager newInstance() {
        return tpm;
    }  
 
    private ThreadPoolManager(){}
 
    public void runConsupmer() {
    	accessBufferThread.run();
    }

}  
