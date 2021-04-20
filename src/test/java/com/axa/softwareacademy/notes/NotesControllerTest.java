package com.axa.softwareacademy.notes;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.mongodb.client.MongoClients;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@ExtendWith(SpringExtension.class)
public class NotesControllerTest {
    private static final String CONNECTION_STRING = "mongodb://%s:%d";

    private MongodExecutable mongodExecutable;
    private MongoTemplate mongoTemplate;

    @AfterEach
    void clean() {
        mongodExecutable.stop();
    }

    @BeforeEach
    void setup() throws Exception {
        String ip = "localhost";
        int port = 27018;

        IMongodConfig mongodConfig = new MongodConfigBuilder().version(Version.Main.PRODUCTION)
                .net(new Net(ip, port, Network.localhostIsIPv6()))
                .build();

        MongodStarter starter = MongodStarter.getDefaultInstance();
        mongodExecutable = starter.prepare(mongodConfig);
        mongodExecutable.start();
        mongoTemplate = new MongoTemplate(MongoClients.create(String.format(CONNECTION_STRING, ip, port)), "test");
    }

    @Test
    public void addNote(@Autowired MongoTemplate mongoTemplate) {
        // given
        DBObject noteToSave = BasicDBObjectBuilder.start()
                .add("noteDetail", "Testosterone")
                .add("patientId", "1")
                .get();

        // when
        mongoTemplate.save(noteToSave, "collection");

        // then
        assertThat(mongoTemplate.findAll(DBObject.class, "collection")).extracting("noteDetail")
                .containsOnly("Testosterone");
        assertThat(mongoTemplate.findAll(DBObject.class, "collection")).extracting("patientId")
                .containsOnly("1");
    }

    @Test
    public void modifyNote(@Autowired MongoTemplate mongoTemplate) {
        // given
        DBObject noteToSave = BasicDBObjectBuilder.start()
                .add("_id", "111111")
                .add("noteDetail", "Testosterone")
                .add("patientId", "2")
                .get();
        mongoTemplate.save(noteToSave, "collection");

        DBObject noteToModify = BasicDBObjectBuilder.start()
                .add("_id", "111111")
                .add("noteDetail", "Testosteroneeee")
                .add("patientId", "2")
                .get();

        // when
        mongoTemplate.save(noteToModify, "collection");

        // then
        assertThat(mongoTemplate.findAll(DBObject.class, "collection")).extracting("noteDetail")
                .contains("Testosteroneeee");
        assertThat(mongoTemplate.findAll(DBObject.class, "collection")).extracting("patientId")
                .contains("2");
    }

    @Test
    public void deleteNote(@Autowired MongoTemplate mongoTemplate) {
        // given
        DBObject noteToSave = BasicDBObjectBuilder.start()
                .add("_id", "111111")
                .add("noteDetail", "Testosterone")
                .add("patientId", "6")
                .get();
        mongoTemplate.save(noteToSave, "collection");

        // when
        mongoTemplate.remove(noteToSave, "collection");

        // then
        assertThat(mongoTemplate.findAll(DBObject.class, "collection")).extracting("patientId")
                .doesNotContain("6");
    }

    @Test
    public void findNoteList(@Autowired MongoTemplate mongoTemplate) {
        // given
        DBObject noteToSave = BasicDBObjectBuilder.start()
                .add("_id", "111111")
                .add("noteDetail", "Coeur")
                .add("patientId", "4")
                .get();

        DBObject noteToSave2 = BasicDBObjectBuilder.start()
                .add("_id", "222222")
                .add("noteDetail", "Foie")
                .add("patientId", "5")
                .get();

        // when
        mongoTemplate.save(noteToSave, "collection");
        mongoTemplate.save(noteToSave2, "collection");

        // then
        assertThat(mongoTemplate.findAll(DBObject.class, "collection")).extracting("patientId")
                .containsAnyOf("4", "5");
        assertThat(mongoTemplate.findAll(DBObject.class, "collection")).extracting("noteDetail")
                .containsAnyOf("Coeur", "Foie");
    }

    @Test
    public void findbyPatientId(@Autowired MongoTemplate mongoTemplate) {
        // given
        DBObject noteToSave = BasicDBObjectBuilder.start()
                .add("_id", "111111")
                .add("noteDetail", "Testosterone")
                .add("patientId", "3")
                .get();

        // when
        mongoTemplate.save(noteToSave, "collection");

        // then
        assertThat(mongoTemplate.findAll(DBObject.class, "collection")).extracting("patientId")
                .containsAnyOf("3");
    }

}
