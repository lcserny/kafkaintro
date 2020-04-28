package net.cserny.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {

    private final KafkaProducer kafkaProducer;

    @Autowired
    public KafkaController(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    @PostMapping("/produce")
    public String produceMessage(@RequestBody String body) {
        kafkaProducer.send(String.valueOf(System.currentTimeMillis()), body);
        return "SENT!";
    }
}
