package ru.inno.stc;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MapReduceIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.codecs.pojo.PojoCodecProvider;
import ru.inno.stc.entity.Post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoDbProducer {
    private static final String        COLLECTION_NAME = "InnoCollection2";
    private              MongoClient   client;
    private              MongoDatabase database;

    public void init() {
        client   = new MongoClient();
        database = client.getDatabase("innodb")
                .withCodecRegistry(
                        fromRegistries(
                                MongoClient.getDefaultCodecRegistry(),
                                fromProviders(PojoCodecProvider.builder().register(Post.class).build())
                        )
                );
    }

    public void close() {
        client.close();
    }

    public void insert(String field1, String field2) {
        final MongoCollection<BasicDBObject> collection = database.getCollection(COLLECTION_NAME, BasicDBObject.class);
        BasicDBObject                        insert     = new BasicDBObject();
        insert.put("field1", field1);
        insert.put("field2", field2);
        collection.insertOne(insert);
    }

    public List<String> getAll() {
        final MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);
        List<String>                    result     = new ArrayList<>();
        for (Document doc : collection.find()) {
            result.add(doc.toJson());
        }
        return result;
    }

    public void insert(Post post) {
        final MongoCollection<Post> collection = database.getCollection(COLLECTION_NAME, Post.class);
        collection.insertOne(post);
    }


    public List<String> findByComment(String s) {
        final MongoCollection<Post> collection = database.getCollection(COLLECTION_NAME, Post.class);
        BasicDBObject                        search     = new BasicDBObject();
        search.put("comments", new BasicDBObject("$in", Collections.singletonList(s)));
        List<String> result = new ArrayList<>();
        for (Post doc : collection.find(search)) {
            result.add(doc.toString());
        }
        return result;
    }

    public List<String> mapReduce(String map, String reduce) {
        final MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);
        List<String> result = new ArrayList<>();
        for (Document doc : collection.mapReduce(map, reduce)) {
            result.add(doc.toJson());
        }
        return result;
    }
}
