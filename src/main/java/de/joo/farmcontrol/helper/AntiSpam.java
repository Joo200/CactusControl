package de.joo.farmcontrol.helper;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class AntiSpam<T> {
    private HashMap<T, Long> blocked;
    private int timeToBlock;

    public AntiSpam(int msToBlock) {
        this.blocked = new HashMap();
        this.timeToBlock = msToBlock;
    }

    public AntiSpam() {
        this(5000);
    }

    public boolean isBlocked(T object) {
        if (this.timeToBlock > 0) {
            int targetHash = object.hashCode();
            Long expireTime = null;
            long now = Calendar.getInstance().getTimeInMillis();
            Iterator entryIterator = this.blocked.entrySet().iterator();

            while(entryIterator.hasNext()) {
                Entry<T, Long> entry = (Entry)entryIterator.next();
                if (((Long)entry.getValue()).longValue() < now) {
                    entryIterator.remove();
                } else if (expireTime == null && entry.getKey().hashCode() == targetHash) {
                    expireTime = (Long)entry.getValue();
                }
            }

            return expireTime != null;
        } else {
            return this.blocked.containsKey(object);
        }
    }

    public void setBlocked(T object) {
        this.blocked.put(object, Calendar.getInstance().getTimeInMillis() + (long)this.timeToBlock);
    }

    public void removeBlocked(T object) {
        this.blocked.remove(object);
    }

    public boolean tryBlock(T object) {
        if (this.isBlocked(object)) {
            return false;
        } else {
            this.setBlocked(object);
            return true;
        }
    }
}
