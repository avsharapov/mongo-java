package ru.inno.stc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.inno.stc.entity.Post;

import java.util.Arrays;
import java.util.List;

public class App {
    private static final Logger logger = LoggerFactory.getLogger("App");

    public static void main(String[] args) {
        MongoDbProducer producer = new MongoDbProducer();
        producer.init();

        String field1 = "value1";
        String field2 = "value2";
        producer.insert(field1, field2);


        producer.insert(new Post("Post 1", 5, Arrays.asList("Comment 1 (post 1)", "Co~~~~mment 2 (post 1)")));
        producer.insert(new Post("Post 2", 15, Arrays.asList("Comment 1 (post 2)", "Comment 2 (post 2)")));
        producer.insert(new Post("Post 3", 25, Arrays.asList("Comment 1 (post 3)", "Comment 2 (post 3)")));


        logger.info("================findByComment===================");
        logger.info("{}", producer.findByComment("Comment 1 (post 1)"));
        logger.info("=================findAll==================");
        logger.info("{}", producer.getAll());

        String map = "function (){"
                         + "if ( this.likes > 10 ) {"
                         + "emit(this.title,this.likes);" + "}" + "};";

        String reduce = "function(key, values) { " +
                            "    return Array.sum(values); " +
                            "}";

        logger.info("=================mapReduce==================");
        logger.info("{}", producer.mapReduce(map, reduce));
        producer.close();
    }
}
