package com.arakviel.localblog.persistence.repository.impl.json;

import com.arakviel.localblog.persistence.entity.Entity;
import com.arakviel.localblog.persistence.exception.JsonFileIOException;
import com.arakviel.localblog.persistence.repository.RepositoryFactory;
import com.arakviel.localblog.persistence.repository.contracts.CommentRepository;
import com.arakviel.localblog.persistence.repository.contracts.LikeRepository;
import com.arakviel.localblog.persistence.repository.contracts.PostRepository;
import com.arakviel.localblog.persistence.repository.contracts.TagRepository;
import com.arakviel.localblog.persistence.repository.contracts.UserRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Set;

/**
 * це реалізація патерну UnitOfWork, та також фабрики з сінглтоном.
 */
public final class JsonRepositoryFactory extends RepositoryFactory {

    private final Gson gson;
    private final CommentJsonRepositoryImpl commentJsonRepositoryImpl;
    private final LikeJsonRepositoryImpl likeJsonRepositoryImpl;
    private final PostJsonRepositoryImpl postJsonRepositoryImpl;
    private final TagJsonRepositoryImpl tagJsonRepositoryImpl;
    private final UserJsonRepositoryImpl userJsonRepositoryImpl;

    private JsonRepositoryFactory() {
        // Адаптер для типу даних LocalDateTime при серіалізації/десеріалізації
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class,
                (JsonSerializer<LocalDateTime>) (localDate, srcType, context) ->
                        new JsonPrimitive(
                                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm").format(localDate)));
        gsonBuilder.registerTypeAdapter(LocalDateTime.class,
                (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) ->
                        LocalDateTime.parse(json.getAsString(),
                                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
                                        .withLocale(Locale.of("uk", "UA"))));

        // Адаптер для типу даних LocalDate при серіалізації/десеріалізації
        gsonBuilder.registerTypeAdapter(LocalDate.class,
                (JsonSerializer<LocalDate>) (localDate, srcType, context) ->
                        new JsonPrimitive(
                                DateTimeFormatter.ofPattern("dd-MM-yyyy").format(localDate)));
        gsonBuilder.registerTypeAdapter(LocalDate.class,
                (JsonDeserializer<LocalDate>) (json, typeOfT, context) ->
                        LocalDate.parse(json.getAsString(),
                                DateTimeFormatter.ofPattern("dd-MM-yyyy")
                                        .withLocale(Locale.of("uk", "UA"))));

        gson = gsonBuilder.setPrettyPrinting().create();

        commentJsonRepositoryImpl = new CommentJsonRepositoryImpl(gson);
        likeJsonRepositoryImpl = new LikeJsonRepositoryImpl(gson);
        postJsonRepositoryImpl = new PostJsonRepositoryImpl(gson);
        tagJsonRepositoryImpl = new TagJsonRepositoryImpl(gson);
        userJsonRepositoryImpl = new UserJsonRepositoryImpl(gson);
    }

    public static JsonRepositoryFactory getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public CommentRepository getCommentRepository() {
        return commentJsonRepositoryImpl;
    }

    @Override
    public LikeRepository getLikeRepository() {
        return likeJsonRepositoryImpl;
    }

    @Override
    public PostRepository getPostRepository() {
        return postJsonRepositoryImpl;
    }

    @Override
    public TagRepository getTagRepository() {
        return tagJsonRepositoryImpl;
    }

    @Override
    public UserRepository getUserRepository() {
        return userJsonRepositoryImpl;
    }

    public void commit() {
        serializeEntities(commentJsonRepositoryImpl.getPath(), commentJsonRepositoryImpl.findAll());
        serializeEntities(likeJsonRepositoryImpl.getPath(), likeJsonRepositoryImpl.findAll());
        serializeEntities(postJsonRepositoryImpl.getPath(), postJsonRepositoryImpl.findAll());
        serializeEntities(tagJsonRepositoryImpl.getPath(), tagJsonRepositoryImpl.findAll());
        serializeEntities(userJsonRepositoryImpl.getPath(), userJsonRepositoryImpl.findAll());
    }

    private <E extends Entity> void serializeEntities(Path path, Set<E> entities) {
        try (FileWriter writer = new FileWriter(path.toFile())) {
            // Скидуємо файлик, перед збереженням!
            writer.write("");
            // Перетворюємо колекцію користувачів в JSON та записуємо у файл
            gson.toJson(entities, writer);

        } catch (IOException e) {
            throw new JsonFileIOException("Не вдалось зберегти дані у json-файл. Детальніше: %s"
                    .formatted(e.getMessage()));
        }
    }

    private static class InstanceHolder {

        public static final JsonRepositoryFactory INSTANCE = new JsonRepositoryFactory();
    }
}
