package info.source4code.soap.http.cxf;

import info.source4code.wsdl.helloworld.Greeting;
import info.source4code.wsdl.helloworld.HelloWorldPort;
import info.source4code.wsdl.helloworld.Person;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelloWorldClient {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(HelloWorldClient.class);

    @Autowired
    private HelloWorldPort clientBean;

    public String sayHello(Person person) {
        Greeting greeting = clientBean.sayHello(person);

        String result = greeting.getText();
        LOGGER.info("result={}", result);
        return result;
    }
}
