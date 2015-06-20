package com.restbucks.gradeappeal.repositories;

import java.util.HashMap;
import java.util.Map.Entry;

import com.restbucks.gradeappeal.model.Identifier;
import com.restbucks.gradeappeal.model.Reply;

public class ReplyRepository {

    private static final ReplyRepository theRepository = new ReplyRepository();
    private HashMap<String, Reply> backingStore = new HashMap<String, Reply>();

    public static ReplyRepository current() {
        return theRepository;
    }
    
    private ReplyRepository(){}
    
    public Reply get(Identifier identifier) {
        return backingStore.get(identifier.toString());
    }
    
    public Reply take(Identifier identifier) {
        Reply reply = backingStore.get(identifier.toString());
        remove(identifier);
        return reply;
    }

    public Identifier store(Reply reply) {
        Identifier id = new Identifier();
        backingStore.put(id.toString(), reply);
        return id;
    }
    
    public void store(Identifier replyIdentifier, Reply reply) {
        backingStore.put(replyIdentifier.toString(), reply);
    }

    public boolean has(Identifier identifier) {
        boolean result =  backingStore.containsKey(identifier.toString());
        return result;
    }

    public void remove(Identifier identifier) {
        backingStore.remove(identifier.toString());
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Entry<String, Reply> entry : backingStore.entrySet()) {
            sb.append(entry.getKey());
            sb.append("\t:\t");
            sb.append(entry.getValue());
            sb.append("\n");
        }
        return sb.toString();
    }

    public synchronized void clear() {
        backingStore = new HashMap<String, Reply>();
    }
}
