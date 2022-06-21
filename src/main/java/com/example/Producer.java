package com.example;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.connect.json.JsonSerializer;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.ParseException;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;


public class Producer {
    private static final Logger logger = LogManager.getLogger();
    public static void main(String[] args) throws ParseException {
        logger.info("creating kafka producer");
        Properties props = new Properties();
        props.put(ProducerConfig.CLIENT_ID_CONFIG, AppConfigs.applicationID);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, AppConfigs.bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        String sourceAPath = "src\\main\\java\\com\\example\\SourceA.JSON";
        String sourceBPath = "src\\main\\java\\com\\example\\SourceB.JSON";


        List<Company> sourceA = pullData(sourceAPath);
        List<Company> sourceB = pullData(sourceBPath);

        KafkaProducer<String, Company> producer = new KafkaProducer<String, Company>(props);
        //System.out.println(sourceA.get(0).getClass());
        for(Company company: sourceA){
            ProducerRecord<String, Company> record = new ProducerRecord<String,Company>("goodFellas", company.getTicker(), company);
            producer.send(record);
            String message = "sent message";
            System.out.println(message);
            producer.close();
            }
        }


        // for(Company company : sourceA){
        //     producer.send(new ProducerRecord<>("java-is-pain", company.Ticker, company));
        //     producer.close();
        // }
        // producer.send(new ProducerRecord<>("newTest", 6, "simpleMessage"));
        // producer.close();

    

    public static List<Company> pullData(String path){
        try (BufferedReader readerA = new BufferedReader(new FileReader(path)))  {
            ObjectMapper mapper = new ObjectMapper();
            List<Company> Companies = mapper.readValue(readerA, TypeFactory.defaultInstance().constructCollectionType(List.class, Company.class));
            return Companies;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
