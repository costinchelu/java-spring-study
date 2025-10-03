package main;

import beans.Cat;
import beans.Mall;
import beans.Owner;
import config.ProjectConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Main {

    public static void main(String[] args) {

        try( var context = new AnnotationConfigApplicationContext(ProjectConfig.class)) {
            Cat cat = context.getBean(Cat.class);
            Owner owner = context.getBean(Owner.class);

            // by changing cat's name we will see that also the owner's cat name is changed (so owner actually have the cat
            // from the context (wiring is working by using @Bean annotation)
            cat.setName("Leo");

            System.out.println(cat);
            System.out.println(owner);

            // in case we use the second method we can observe that Spring is autowiring the cat from cat() method)
            // previously we needed @Autowired annotation
            Owner owner2 = context.getBean("owner2", Owner.class);
            System.out.println(owner2);

            // using stereotypes
            Mall mall = context.getBean(Mall.class);
            System.out.println(mall);
        }
	}
}
