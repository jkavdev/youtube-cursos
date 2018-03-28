package br.com.jkavdev.danvega.angular4.tasks.domain;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Task {
	
	@Id @GeneratedValue
	private Long id;
	
	private String name;
	
	@JsonFormat(pattern = "MM/dd/yyyy")
	private LocalDate dueDate;
	
	private Boolean completed;

	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public LocalDate getDueDate() {
		return dueDate;
	}
	public Boolean getCompleted() {
		return completed;
	}

}
