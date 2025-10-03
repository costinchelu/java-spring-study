package main;

import autowire.services.ProductDeliveryService;
import beans.MyBean;
import beans.OtherBean;
import config.ProjectConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {


    /**
     * XML
     * Annotations
     *
     * without configuration, just using a bean is not possible.
     * We will get org.springframework.beans.factory.NoSuchBeanDefinitionException
     *
     * org.springframework.beans.factory.NoUniqueBeanDefinitionException will be thrown when more than one bean
     * of the same class are defined in the configuration class
     *
     *
     */
    public static void main(String[] args) {
        try (var context = new AnnotationConfigApplicationContext(ProjectConfig.class)) {

            // INTRODUCTION:

            // get bean by type:
            MyBean b1 = context.getBean(MyBean.class);
            System.out.println(b1.getText());

            // get bean by name:
            MyBean b2 = context.getBean("myBean2", MyBean.class);
            System.out.println(b2.getText());

            // get bean by name, but by naming the actual bean in the configuration class:
            MyBean b3 = context.getBean("B3", MyBean.class);
            System.out.println(b3.getText());


            // using stereotype
            OtherBean b4 = context.getBean(OtherBean.class);
            System.out.println(b4.getText());

            // for example, a normal initialisation, without using the CONTEXT will get us a normal object
            // in this case we can observe that @PostConstruct has no importance (outside the context)
            OtherBean b5 = new OtherBean();
            System.out.println(b5.getText());
            b5.setText("Hello 5");
            System.out.println(b5.getText());


            // AUTOWIRING:
            // take from the context a value and use it

            // get bean by type
            ProductDeliveryService service = context.getBean(ProductDeliveryService.class);
            service.addSomeProducts();
        }
    }
}
