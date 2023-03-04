package john.alpha.outer;

import john.beta.inner.InnerApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Objects;

@SpringBootApplication
@RestController
public class OuterApplication {

    private ConfigurableApplicationContext context = null;

    private Process otherAppProcess = null;

    public static void main(String[] args) {
        SpringApplication.run(OuterApplication.class, args);
    }

    @GetMapping("/hello")
    public String helloWorld() {
        return "hello world!";
    }

    @GetMapping("/run")
    public String run() throws IOException {
        if (otherAppProcess != null) {
            stop();
        }
        var processBuilder = new ProcessBuilder("java", "-jar", "inner-0.0.1-SNAPSHOT.jar");
        File directory = new File("C:\\Users\\johnf\\Desktop");
//        File directory = new File("src/main/resources");
        System.out.println(directory.getAbsolutePath());
        processBuilder.directory(directory.getAbsoluteFile());
        processBuilder.redirectErrorStream(true);

        otherAppProcess = processBuilder.start();

        return "running";
    }

    @GetMapping("/stop")
    public String stop() {
        if (otherAppProcess != null) {
            otherAppProcess.destroy();
            otherAppProcess = null;
        }
        return "stopped";
    }

//    @GetMapping("/run")
//    public String run() {
////        context = SpringApplication.run(OuterApplication.class);
//        InnerApplication.main(new String[]{});
//        return "running";
//    }
//
//    @GetMapping("/stop")
//    public String stop() {
//		context.close();
//		context = null;
//        return "stopped";
//    }

}
