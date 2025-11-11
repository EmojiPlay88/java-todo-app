package org.mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class TaskService {

    @Inject MongoClient mongoClient;

    public List<Task> list(){
        List<Task> tasks = new ArrayList<>();
        MongoCursor<Document> cursor = getCollection().find().iterator();

        try {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                Task task = new Task(
                        document.getString("title"),
                        document.getString("description"),
                        document.getObjectId("_id").toString()
                );
                tasks.add(task);
            }
        } finally {
            cursor.close();
        }
        System.out.println(tasks);
        return tasks;
    }

    public void add(Task task){
        Document document = new Document()
                .append("title", task.getTitle())
                .append("description", task.getDescription());
        getCollection().insertOne(document);
    }

    public void delete(String id){
        getCollection().deleteMany(new Document("_id", new ObjectId(id)));
    }

    private MongoCollection getCollection() { return mongoClient.getDatabase("tasks").getCollection("tasks"); }
}
