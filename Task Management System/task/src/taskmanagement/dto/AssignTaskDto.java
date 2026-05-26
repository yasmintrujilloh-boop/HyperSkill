package taskmanagement.dto;

import jakarta.validation.constraints.NotBlank;
//Request
public class AssignTaskDto {

    @NotBlank
    private String assignee;

    public AssignTaskDto(){

    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }
}