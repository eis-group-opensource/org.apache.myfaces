/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.component.html.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.myfaces.component.html.util.StreamingAddResource.StreamablePositionedInfo;

public class StreamingThreadManager
{
    public final static String KEY = "org.apache.myfaces.component.html.util.StreamingThreadManager";
    
    /**
     * central place where all request store their "to be added" stylesheets
     */
    private final Map headerInfos = new HashMap();
    
    private Thread cleanupThread = null;
    
    private CleanupThread cleanupThreadObject = null;
    
    /**
     * request counter
     */
    private volatile long REQUEST_ID_COUNTER = 0;

    public StreamingThreadManager()
    {
    }
    
    public void init()
    {
        if (cleanupThread == null)
        {
            cleanupThreadObject = new CleanupThread();
            cleanupThread = new Thread(cleanupThreadObject, "StreamingAddResource.CleanupThread");
            cleanupThread.setDaemon(true);
            cleanupThread.start();
        }
    }
    
    public void destroy()
    {
        if (cleanupThread != null)
        {
            cleanupThreadObject.done();
            cleanupThread.interrupt();
            cleanupThread = null;
            synchronized (headerInfos)
            {
                headerInfos.clear();
            }
        }
    }
    
    public HeaderInfoEntry getHeaderInfo(Long requestId)
    {
        synchronized (headerInfos)
        {
            return (HeaderInfoEntry) headerInfos.get(requestId);
        }
    }
    
    public Long putNewHeaderInfoEntry()
    {
        Long requestId = null;
        synchronized(this)
        {
            REQUEST_ID_COUNTER++;
            requestId = new Long(REQUEST_ID_COUNTER);
        }
        HeaderInfoEntry headerInfoEntry = new HeaderInfoEntry();
        synchronized (headerInfos)
        {
            headerInfos.put(requestId, headerInfoEntry);
        }
        return requestId;
    }

    public void removeHeaderInfo(Long requestId)
    {
        synchronized (headerInfos)
        {
            headerInfos.remove(requestId);
        }
    }
    
    public static class HeaderInfoEntry
    {
        private final long destroyTime = System.currentTimeMillis() + (1000 * 60); // one minute;
        private final List addedInfos = new ArrayList(10);
        private volatile boolean requestDone = false;

        protected HeaderInfoEntry()
        {
        }

        protected boolean isDestroyable(long now)
        {
            return destroyTime < now;
        }

        protected void addInfo(StreamablePositionedInfo positionedInfo)
        {
            synchronized (addedInfos)
            {
                addedInfos.add(positionedInfo);
                addedInfos.notifyAll();
            }
        }

        protected StreamablePositionedInfo fetchInfo() throws InterruptedException
        {
            synchronized (addedInfos)
            {
                while (addedInfos.size() < 1 && !requestDone)
                {
                    addedInfos.wait(100);
                }
                if (addedInfos.size() < 1)
                {
                    // request done
                    return null;
                }

                return (StreamablePositionedInfo) addedInfos.remove(0);
            }
        }

        protected void setRequestDone()
        {
            requestDone = true;
        }
    }
    
    private class CleanupThread implements Runnable
    {
        // how many entries should be removed per run
        private final static int CHECKS_PER_RUN = 10;

        // but never reach this maximum
        private final static int CACHE_LIMIT = 1000;
        
        private boolean threadDone = false;

        public void run()
        {
            while (!Thread.interrupted() && !threadDone)
            {
                checkMap();

                try
                {
                    Thread.sleep(1000 * 30); // check every 30 sek
                }
                catch (InterruptedException e)
                {
                    // ignore
                }
            }
        }
        
        public void done() {
            threadDone = true;
        }

        private void checkMap()
        {
            synchronized (headerInfos)
            {
                long now = System.currentTimeMillis();

                int checkNo = 0;
                Iterator iterEntries = headerInfos.entrySet().iterator();
                while (iterEntries.hasNext() && !Thread.currentThread().isInterrupted())
                {
                    checkNo++;
                    if (headerInfos.size() < CACHE_LIMIT && checkNo > CHECKS_PER_RUN)
                    {
                        return;
                    }
                    Map.Entry entry = (Map.Entry) iterEntries.next();
                    HeaderInfoEntry headerInfoEntry = (HeaderInfoEntry) entry.getValue();
                    if (headerInfoEntry.isDestroyable(now))
                    {
                        iterEntries.remove();
                    }
                }
            }
        }
    }
}
