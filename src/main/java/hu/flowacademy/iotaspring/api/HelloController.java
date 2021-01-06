package hu.flowacademy.iotaspring.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@RestController
public class HelloController {

    private List<Request> dataStore = new ArrayList<>();

    @GetMapping("/hello")
    public String sayHello() {
        return "hello";
    }

    @GetMapping("/hello/{name}")
    // vagy a valtozo neve vagy a path variable erteke meg kell
    // hogy egyezzen a get mappingben levo (placeholder) nevvel
    public String sayHelloTo(@PathVariable("name") String name) {
        return "hello " + name;
    }

    @GetMapping("/randomNumber")
    public int getRandomNumberBetween(@RequestParam("from") int from, @RequestParam("to") int to) {
        return (from + new Random().nextInt(to) + 1) - from;
    }

    @PostMapping("/save")
    public Request save(@RequestBody Request body) {
        log.info(body.toString());
        dataStore.add(body);
        return body;
    }

    @GetMapping("/findAll")
    public List<Request> findAll(@RequestHeader(value = "Origin", required = false) String origin) {
        log.info("Origin is: " + origin);
        return dataStore;
    }

}
