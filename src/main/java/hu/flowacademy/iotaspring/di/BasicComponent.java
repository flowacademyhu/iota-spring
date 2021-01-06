package hu.flowacademy.iotaspring.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BasicComponent {

    // Barmikor el tudod erni a masik komponenst
    // csak @Autowired-el kell hivatkozni ra
    @Autowired
    private OtherComponent otherComponent;

    public String name = "basic controller";

    public String getNames() {
        return name
                + System.lineSeparator()
                + otherComponent.name;
    }

}
