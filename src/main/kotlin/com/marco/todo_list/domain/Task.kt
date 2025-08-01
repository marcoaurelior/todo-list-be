package com.marco.todo_list.domain

import com.github.f4b6a3.uuid.UuidCreator
import com.marco.todo_list.application.repository.TaskRepository
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.hibernate.Hibernate
import java.math.BigDecimal
import java.time.LocalDate

@Entity
data class Task(
    @Id val id: String = UuidCreator.getTimeOrderedEpoch().toString(),

    var name: String? = null,
    var cost: BigDecimal? = null,
    var dueDate: LocalDate? = null,
    var displayOrder: Int? = null
) {

    companion object {
        fun create(task: Task, repository: TaskRepository): Task {
            return repository.save(task)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Task

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    override fun toString(): String {
        return "Task(id='$id', name='$name', cost='$cost', dueDate=$dueDate, displayOrder=$displayOrder)"
    }
}