package br.com.jkavdev.danvega.angular4.tasks.service;

import br.com.jkavdev.danvega.angular4.tasks.domain.Task;
import br.com.jkavdev.danvega.angular4.tasks.repository.TaskRepository;

public class TaskServiceImpl implements TaskService {

	private TaskRepository repository;

	public TaskServiceImpl(TaskRepository repository) {
		this.repository = repository;
	}

	@Override
	public Iterable<Task> list() {
		return repository.findAll();
	}

}
