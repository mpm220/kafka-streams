package com.example;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import io.confluent.kafka.streams.serdes.avro.GenericAvroSerde;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;

import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.connect.json.JsonSerializer;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.ParseException;
import com.example.Company;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;


public class Producer {
    private static final Logger logger = LogManager.getLogger();
    public static void main(String[] args) throws ParseException {
        logger.info("creating kafka producer");
        Properties props = new Properties();
        props.put(ProducerConfig.CLIENT_ID_CONFIG, AppConfigs.applicationID);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, AppConfigs.bootstrapServers);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.JsonSerializer");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, SpecificAvroSerde.class);
        

        String sourceAPath = "src\\main\\java\\com\\example\\SourceA.JSON";
        String sourceBPath = "src\\main\\java\\com\\example\\SourceB.JSON";

        // final Map<String, String> serdeConfig = Collections.singletonMap("schema.registry.url", "http://my-schema-registry:8081");
        // final Serde<String> stringSerde = Serdes.String();
        // stringSerde.configure(serdeConfig, true);
        // final Serde<Company> valueGenericAvroSerde = new SpecificAvroSerde<Company>();
        //valueGenericAvroSerde.configure(serdeConfig, false);
        KafkaProducer<String, Company> producer = new KafkaProducer<>(props);
        List<Company> sourceA = pullData(sourceAPath);
        for(Company company: sourceA){
            producer.send(new ProducerRecord<String,Company>("test", company.getTicker(), company));
            producer.close();
        }
       

        // StreamsBuilder builder = new StreamsBuilder();
        // KStream<String, Company> kStream = builder.stream( "test");
        // kStream.foreach((k,v) -> System.out.println("key = " + k + " value = " + v));




        // StreamsBuilder streamsBuilder = new StreamsBuilder();
        // KStream<Integer,String> kStream = streamsBuilder.stream("sourceA");
        // kStream.foreach((k,v) -> System.out.println("key = " + k + " value = " + v));

        // Topology topology = streamsBuilder.build();
        // KafkaStreams streams = new KafkaStreams(topology, props);
        // streams.start();

        // Runtime.getRuntime().addShutdownHook(new Thread(()->{
        //     System.out.println("closing stream");
        //     streams.close();
        // }));
        




        // List<Company> sourceA = pullData(sourceAPath);
        // List<Company> sourceB = pullData(sourceBPath);

  
        
        }


       
        

    

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
