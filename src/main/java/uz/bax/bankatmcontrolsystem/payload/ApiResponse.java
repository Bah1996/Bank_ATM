package uz.bax.bankatmcontrolsystem.payload;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {

    public ApiResponse(String message, boolean status) {
        this.message = message;
        this.status = status;
    }

    private String message;
    private boolean status;
    private Object object;
}
