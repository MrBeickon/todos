package com.desafio.todos.loader;

import com.desafio.todos.model.document.Priority;
import com.desafio.todos.model.document.Status;
import com.desafio.todos.model.document.Task;
import com.desafio.todos.repository.TaskRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader {

    @Bean
    @Order(1)
    CommandLineRunner preloadRol(TaskRepository taskRepository) {
        return args -> {
            if (taskRepository.count() == 0) {

                var tarea1 = new Task();

                tarea1.setTitle("Primera Tarea");
                tarea1.setDescription("con descripcion");
                tarea1.setPriority(Priority.ALTA);
                tarea1.setExpirationDate(LocalDate.now().plusDays(1));
                tarea1.setStatus(Status.PENDIENTE);

                taskRepository.save(tarea1);

                var tarea2 = new Task();

                tarea2.setTitle("Segunda Tarea");
                tarea2.setPriority(Priority.BAJA);
                tarea2.setExpirationDate(LocalDate.now().plusDays(5));
                tarea2.setStatus(Status.EN_CURSO);

                taskRepository.save(tarea2);

                var tarea3 = new Task();

                tarea3.setTitle("Tercera Tarea");
                tarea3.setPriority(Priority.BAJA);
                tarea3.setExpirationDate(LocalDate.now().plusDays(4));
                tarea3.setStatus(Status.COMPLETADA);

                taskRepository.save(tarea3);

                var tarea4 = new Task();

                tarea4.setTitle("Cuarta Tarea");
                tarea4.setDescription("con descripcion");
                tarea4.setPriority(Priority.ALTA);
                tarea4.setExpirationDate(LocalDate.now().plusDays(10));
                tarea4.setStatus(Status.EN_CURSO);

                taskRepository.save(tarea4);

                var tarea5 = new Task();

                tarea5.setTitle("Quinta Tarea");
                tarea5.setDescription("con descripcion");
                tarea5.setPriority(Priority.BAJA);
                tarea5.setExpirationDate(LocalDate.now().plusDays(3));
                tarea5.setStatus(Status.COMPLETADA);

                taskRepository.save(tarea5);
            }
        };
    }
}
