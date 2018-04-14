package ru.game.pattern.resource.provider;

import java.io.InputStream;

public interface ResourceProvider {
    InputStream getResource(String path);
}
