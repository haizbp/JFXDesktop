package hm;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
@Component
public class App extends Application {

    private ConfigurableApplicationContext context;
    private Parent rootNode;
    private FXMLLoader loader;
    @Autowired
    private Environment environment;
    private AppHelper appConfiguration;
   
    @Override
    public void init() throws Exception {
        context = SpringApplication.run(App.class);
        environment = context.getEnvironment();
        appConfiguration = new AppHelper(environment);
  
        
        loader = new FXMLLoader();
        loader.setControllerFactory(context::getBean);
    }

    @Override
    public void start(Stage stage) throws Exception {
        loader.setLocation(new File("config\\resources\\fxml\\main.fxml").toURI().toURL());
        rootNode = loader.load();
        Scene scene = new Scene(rootNode, appConfiguration.getWindowX(),
                appConfiguration.getWindowY());
        
        appConfiguration.addNode("loader", loader);
        appConfiguration.addNode("navUI", loader.getNamespace().get("navUI"));
        appConfiguration.addNode("menubarUI", loader.getNamespace().get("menubarUI"));
        appConfiguration.addNode("scene", scene);
        appConfiguration.initUI();
        

//        scene.getStylesheets().add("file:/D:/Workspace/Netbean/HIDE/HIDE/config/resources/fxml/css/styles.css");
        stage.setTitle(appConfiguration.getAppTitle());
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        context.close();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
