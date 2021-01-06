package hu.flowacademy.iotaspring.api;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Request {

    private String name;
    private int age;
    private String nation;

}
