package taskmanagement.dto;

import jakarta.validation.constraints.NotNull;
import taskmanagement.modelo.Estatus;

public class UpdateStatusDto {

    @NotNull
    private Estatus status;

    public Estatus getStatus() {
        return status;
    }

    public void setStatus(Estatus status) {
        this.status = status;
    }
}