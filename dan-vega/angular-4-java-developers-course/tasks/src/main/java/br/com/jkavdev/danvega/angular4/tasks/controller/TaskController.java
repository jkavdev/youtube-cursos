package br.com.jkavdev.danvega.angular4.tasks.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jkavdev.danvega.angular4.tasks.domain.Task;
import br.com.jkavdev.danvega.angular4.tasks.service.TaskService;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

	private TaskService service;

	public TaskController(TaskService service) {
		this.service = service;
	}

	@GetMapping(value = { "", "/" })
	public Iterable<Task> listTasks() {
		return service.list();
	}

}
