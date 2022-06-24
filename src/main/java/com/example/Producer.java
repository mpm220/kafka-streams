package com.example;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.ParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;


public class Producer {
    private static final Logger logger = LogManager.getLogger();
    public static void main(String[] args) throws ParseException {
        // data extraction
        String sourceAPath = "src\\main\\java\\com\\example\\SourceA.JSON";
        String sourceBPath = "src\\main\\java\\com\\example\\SourceB.JSON";
        ArrayList<Company> sourceA = pullData(sourceAPath, "A");
        ArrayList<Company> sourceB = pullData(sourceBPath, "B");
        Company company = sourceA.get(0);
        
        //send trial message using custom serdes
        KafkaProducer<String, Company> producer = createKafkaProducer();
        producer.send(new ProducerRecord<String,Company>("sourceA",company.getTicker(), company));
        System.out.println("message sent");
        producer.close();

        // consume trial message using custom serdes
        AtomicReference<Company> msgCons = new AtomicReference<>();
        KafkaConsumer<String, Company> consumer = createKafkaConsumer();
        consumer.subscribe(Arrays.asList("java"));
        ConsumerRecords<String, Company> records = consumer.poll(Duration.ofSeconds(1));
        records.forEach(record -> {
            msgCons.set(record.value());
            System.out.println("Message received " + record.value());
        });
        consumer.close();
    }

    private static KafkaProducer<String, Company> createKafkaProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.CLIENT_ID_CONFIG, AppConfigs.applicationID);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, AppConfigs.bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "com.example.CustomSerializer");
        // props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        // props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, SpecificAvroSerde.class);
        return new KafkaProducer<>(props);
    }

    private static KafkaConsumer<String, Company> createKafkaConsumer(){
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, AppConfigs.bootstrapServers);
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, AppConfigs.applicationID);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "group");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "com.example.CustomDeserializer");
        return new KafkaConsumer<>(props);
    }

    public static ArrayList<Company> pullData(String path, String source){
        try (BufferedReader readerA = new BufferedReader(new FileReader(path)))  {
            ObjectMapper mapper = new ObjectMapper();
            if (source == "A"){
                ArrayList<Company> companies = mapper.readValue(readerA, TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, CompanySA.class));
                return companies;
            }else{
                ArrayList<Company> companies = mapper.readValue(readerA, TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, CompanySB.class));
                return companies;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
