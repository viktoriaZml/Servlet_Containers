package ru.netology.repository;

import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

// Stub
public class PostRepository {
    private static ConcurrentHashMap<Long, Post> repository = new ConcurrentHashMap<>();
    private static AtomicLong counter = new AtomicLong(0);

    public List<Post> all() {
        Collection<Post> collection = repository.values();
        if (collection == null) {
            return Collections.emptyList();
        }
        return new ArrayList(collection);
    }

    public Optional<Post> getById(long id) {
        if (repository.containsKey(id)) {
            return Optional.of(repository.get(id));
        }
        return Optional.empty();
    }

    public Optional<Post> save(Post post) {
        long id = post.getId();
        if (id == 0) {
            id = counter.incrementAndGet();
            post.setId(id);
            repository.put(id, post);
        } else if (repository.containsKey(id)) {
            repository.replace(post.getId(), post);
        } else
            return Optional.empty();
        return Optional.of(repository.get(id));
    }

    public void removeById(long id) {
        repository.remove(id);
    }
}
