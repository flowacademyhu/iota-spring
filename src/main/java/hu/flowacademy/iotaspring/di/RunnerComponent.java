package hu.flowacademy.iotaspring.di;

import org.springframework.stereotype.Component;

@Component
public class RunnerComponent {
    private final BasicComponent basicComponent;
    private final OtherComponent otherComponent;

    public RunnerComponent(BasicComponent basicComponent,
                           OtherComponent otherComponent) {
        this.basicComponent = basicComponent;
        this.otherComponent = otherComponent;

        otherComponent.name += " edited";
        System.out.println(basicComponent.getNames());
    }
}
