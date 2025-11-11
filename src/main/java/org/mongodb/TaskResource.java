package org.mongodb;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/todolist")
public class TaskResource {

    @Inject
    TaskService taskService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Task> get() {
        return taskService.list();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Task> post(Task task) {
        taskService.add(task);
        return taskService.list();
    }

    @DELETE
    @Path("/{id}")
    public void get(@PathParam("id") String id) {
        taskService.delete(id);
    }
}
