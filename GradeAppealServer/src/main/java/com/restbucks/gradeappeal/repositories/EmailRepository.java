package com.restbucks.gradeappeal.repositories;

import java.util.HashMap;
import java.util.Map.Entry;

import com.restbucks.gradeappeal.model.Identifier;
import com.restbucks.gradeappeal.model.Email;

public class EmailRepository {

    private static final EmailRepository theRepository = new EmailRepository();
    private HashMap<String, Email> backingStore = new HashMap<>();

    public static EmailRepository current() {
        return theRepository;
    }
    
    private EmailRepository(){}
    
    public Email get(Identifier identifier) {
        return backingStore.get(identifier.toString());
    }
    
    public Email take(Identifier identifier) {
        Email email = backingStore.get(identifier.toString());
        remove(identifier);
        return email;
    }

    public Identifier store(Email email) {
        Identifier id = new Identifier();
        backingStore.put(id.toString(), email);
        return id;
    }
    
    public void store(Identifier emailIdentifier, Email email) {
        backingStore.put(emailIdentifier.toString(), email);
    }

    public boolean has(Identifier identifier) {
        boolean result =  backingStore.containsKey(identifier.toString());
        return result;
    }

    public void remove(Identifier identifier) {
        backingStore.remove(identifier.toString());
    }
    
    public boolean emailPlaced(Identifier identifier) {
        return EmailRepository.current().has(identifier);
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Entry<String, Email> entry : backingStore.entrySet()) {
            sb.append(entry.getKey());
            sb.append("\t:\t");
            sb.append(entry.getValue());
            sb.append("\n");
        }
        return sb.toString();
    }

    public synchronized void clear() {
        backingStore = new HashMap<>();
    }
    
    public int size() {
        return backingStore.size();
    }
}
