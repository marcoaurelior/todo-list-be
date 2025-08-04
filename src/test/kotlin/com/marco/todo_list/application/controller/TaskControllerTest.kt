package com.marco.todo_list.application.controller

import IntegratedTest
import com.marco.todo_list.application.service.TaskService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.math.BigDecimal
import java.time.LocalDate

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest : IntegratedTest() {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var taskService: TaskService

    @Test
    fun `should create a task`() {
        val json = javaClass.getResource("/json/task.request/create-task.json")!!.readText()

        val result = mockMvc.perform(
            MockMvcRequestBuilders.post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andReturn()

        val body = result.response.contentAsString
        assert(body.contains("Comprar arroz"))
    }

    @Test
    fun `should get one task by id`() {
        val result = mockMvc.perform(MockMvcRequestBuilders.get("/tasks/11111111-1111-1111-1111-111111111111"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()

        assert(result.response.contentAsString.contains("Comprar feijão no mercado"))
    }

    @Test
    fun `should get all tasks`() {
        val result = mockMvc.perform(MockMvcRequestBuilders.get("/tasks"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()

        assert(result.response.contentAsString.contains("Comprar feijão no mercado"))
    }

    @Test
    fun `should update task`() {
        val task = taskService.create("Nome Antigo", BigDecimal.ONE, LocalDate.now().plusDays(1))

        val json = javaClass.getResource("/json/task.request/update-task.json")!!.readText()

        val result = mockMvc.perform(
            MockMvcRequestBuilders.put("/tasks/${task.id}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()

        assert(result.response.contentAsString.contains("Nome novo"))
    }


    @Test
    fun `should update display orders of tasks`() {
        val json = javaClass.getResource("/json/task.request/update-tasks-order.json")!!.readText()

        mockMvc.perform(
            MockMvcRequestBuilders.put("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)

        val responseTask1 = mockMvc.perform(
            MockMvcRequestBuilders.get("/tasks/11111111-1111-1111-1111-111111111111")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()

        assert(responseTask1.response.contentAsString.contains("\"displayOrder\":2"))

        val responseTask2 = mockMvc.perform(
            MockMvcRequestBuilders.get("/tasks/22222222-2222-2222-2222-222222222222")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()

        assert(responseTask2.response.contentAsString.contains("\"displayOrder\":1"))
    }
}