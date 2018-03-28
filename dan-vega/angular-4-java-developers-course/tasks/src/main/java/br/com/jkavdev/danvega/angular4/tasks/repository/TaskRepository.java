package br.com.jkavdev.danvega.angular4.tasks.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.jkavdev.danvega.angular4.tasks.domain.Task;

public interface TaskRepository extends CrudRepository<Task, Long> {

}
