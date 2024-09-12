package com.rohithkankipati.Inventory.util;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class SecretKeyService {
	
	@Value("${jwt.secret.file}")
    private Resource secretKeyResource;

    public String getSecretKey() throws IOException {
	if (!secretKeyResource.exists()) {
	    throw new IOException("Secret key file not found in classpath");
	}

	try (InputStream inputStream = secretKeyResource.getInputStream()) {
	    byte[] keyBytes = inputStream.readAllBytes();
	    return new String(keyBytes).trim();
	}
    }
}
