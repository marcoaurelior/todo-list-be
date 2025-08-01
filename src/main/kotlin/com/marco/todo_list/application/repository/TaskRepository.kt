package com.marco.todo_list.application.repository

import com.marco.todo_list.domain.Task
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface TaskRepository : JpaRepository<Task, String> {
    @Query("SELECT MAX(task.displayOrder) FROM Task task")
    fun findMaxDisplayOrder(): Int?

    fun existsByName(name: String): Boolean
    fun existsByNameAndIdNot(name: String, id: String): Boolean
}