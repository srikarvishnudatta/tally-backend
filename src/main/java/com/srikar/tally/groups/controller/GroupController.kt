package com.srikar.tally.groups.controller


import com.srikar.tally.configuration.FirebaseUserPrincipal
import com.srikar.tally.expenses.dto.ExpenseDto
import com.srikar.tally.expenses.service.ExpenseService
import com.srikar.tally.groups.dto.GroupDto
import com.srikar.tally.groups.service.GroupService
import com.srikar.tally.groups.dto.NewGroupDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/groups")
class GroupController(
    private val groupService: GroupService,
    private val expenseService: ExpenseService
) {
    @PostMapping("/")
    fun createGroup(
        @AuthenticationPrincipal user: FirebaseUserPrincipal,
        @RequestBody data: NewGroupDto
    ): ResponseEntity<Int>{
        val userId = user.uid
        val result = groupService.createGroup(userId, data)
        return ResponseEntity.ok(result.get())
    }
    @GetMapping("/{groupId}")
    fun getGroupById(
        @PathVariable("groupId") groupId: Int
    ): ResponseEntity<GroupDto>{
        val group = groupService.getGroupById(groupId)
        return ResponseEntity.ok(group.get())
    }
    @GetMapping("/")
    fun getGroupByUser(
        @AuthenticationPrincipal user: FirebaseUserPrincipal
    ): ResponseEntity<List<GroupDto>>{
        val userId = user.uid
        val results = groupService.getUserGroups(userId)
        return ResponseEntity.ok(results)
    }

    @GetMapping("/{groupId}/expenses")
    fun getAllExpensesByGroup(@PathVariable("groupId") groupId: Int){

    }
    @PostMapping("{groupId}/expenses")
    @ResponseStatus(HttpStatus.CREATED)
    fun newExpense(@PathVariable("groupId") groupId: Int,
                   @RequestBody dto: ExpenseDto
    ){
        expenseService.createNewExpense(groupId, dto)
    }
}