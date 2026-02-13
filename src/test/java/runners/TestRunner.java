package runners;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/main/resources/features",
        glue = "br.com.pcdev.steps",
     // tags = "@cenario8", // <--- executar somente cenarios especificos
        plugin = {"pretty"},
        monochrome = true
)
public class TestRunner {
}
