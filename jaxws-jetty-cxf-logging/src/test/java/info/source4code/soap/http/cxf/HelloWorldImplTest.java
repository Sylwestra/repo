package info.source4code.soap.http.cxf;

import static org.junit.Assert.assertEquals;
import info.source4code.wsdl.helloworld.Person;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/META-INF/spring/context-requester.xml" })
public class HelloWorldImplTest {

    private static String ENDPOINT_ADDRESS = "http://localhost:9090/s4c/services/helloworld";

    @Autowired
    private HelloWorldClient clientBean;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

        // start the HelloWorld service using jaxWsServerFactoryBean
        JaxWsServerFactoryBean jaxWsServerFactoryBean = new JaxWsServerFactoryBean();

        // adding logging interceptors to print the received/sent messages
        LoggingInInterceptor loggingInInterceptor = new LoggingInInterceptor();
        loggingInInterceptor.setPrettyLogging(true);
        LoggingOutInterceptor loggingOutInterceptor = new LoggingOutInterceptor();
        loggingOutInterceptor.setPrettyLogging(true);

        jaxWsServerFactoryBean.getInInterceptors().add(loggingInInterceptor);
        jaxWsServerFactoryBean.getOutInterceptors().add(loggingOutInterceptor);

        // setting the implementation
        HelloWorldImpl implementor = new HelloWorldImpl();
        jaxWsServerFactoryBean.setServiceBean(implementor);
        // setting the endpoint
        jaxWsServerFactoryBean.setAddress(ENDPOINT_ADDRESS);
        jaxWsServerFactoryBean.create();
    }

    @Test
    public void testSayHello() {

        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");

        assertEquals("Hello John Doe!", clientBean.sayHello(person));
    }
}
