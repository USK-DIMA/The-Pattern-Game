package ru.game.pattern.resource.provider;

final public class ResourceProviderBinder {

    private static volatile ResourceProvider instance;

    public static ResourceProvider getInstance() {
        if(instance == null) {
            synchronized (ResourceProviderBinder.class) {
                if(instance == null) {
                    instance = new ResourceProviderImpl();
                }
            }
        }
        return instance;
    }
}
