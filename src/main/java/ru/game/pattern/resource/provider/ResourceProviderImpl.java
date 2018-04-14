package ru.game.pattern.resource.provider;

import java.io.InputStream;

public class ResourceProviderImpl implements ResourceProvider {

    private ClassLoader classLoader;

    public ResourceProviderImpl() {
        classLoader = getClass().getClassLoader();
    }

    @Override
    public InputStream getResource(String path) {
        return classLoader.getResourceAsStream(path);
    }

}
