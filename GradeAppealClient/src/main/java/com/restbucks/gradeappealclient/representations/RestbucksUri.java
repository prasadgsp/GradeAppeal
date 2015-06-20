package com.restbucks.gradeappealclient.representations;

import java.net.URI;
import java.net.URISyntaxException;

import com.restbucks.gradeappealclient.model.Identifier;

public class RestbucksUri {
    private URI uri;
    
    public RestbucksUri(String uri) {
        try {
            this.uri = new URI(uri);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    
    public RestbucksUri(URI uri) {
        this(uri.toString());
    }

    public RestbucksUri(URI uri, Identifier identifier) {
        this(uri.toString() + "/" + identifier.toString());
    }

    public Identifier getId() {
        String path = uri.getPath();
        return new Identifier(path.substring(path.lastIndexOf("/") + 1, path.length()));
    }

    public URI getFullUri() {
        return uri;
    }
    
    @Override
    public String toString() {
        return uri.toString();
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof RestbucksUri) {
            return ((RestbucksUri)obj).uri.equals(uri);
        }
        return false;
    }

    public String getBaseUri() {
        
        String uriString = uri.toString();
        String baseURI   = uriString.substring(0, uriString.lastIndexOf("webresources/")+"webresources".length());
        
        return baseURI;
    }
}
