package wi.kafka.sink;

import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.connect.data.Field;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.errors.ConnectException;
import org.apache.kafka.connect.sink.SinkRecord;
import org.apache.kafka.connect.sink.SinkTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wi.kafka.sink.jsonschema.Fullname;
import wi.kafka.sink.jsonschema.Info;
import wi.kafka.sink.jsonschema.Personal;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;


public class MyIndividualSinkTask extends SinkTask {

    private static final Logger log = LoggerFactory.getLogger(MyIndividualSinkTask.class);

    private String filename;

    private final MappingProperties mappingProperties;
    private PrintStream outputStream;

    private final StringMapper stringMapper;

    public MyIndividualSinkTask() {
        filename = null;
        this.mappingProperties = new MappingProperties();
        this.stringMapper = new StringMapper();
    }

    public MyIndividualSinkTask(PrintStream outputStream) {
        filename = null;
        this.mappingProperties = new MappingProperties();
        this.stringMapper = new StringMapper();
        this.outputStream = outputStream;
    }

    @Override
    public String version() {
        return new MySinkConnector().version();
    }

    @Override
    public void start(Map<String, String> props) {
        AbstractConfig config = new AbstractConfig(MySinkConnector.CONFIG_DEF, props);
        filename = config.getString(MySinkConnector.FILE_CONFIG);
        if (filename == null || filename.isEmpty()) {
            outputStream = System.out;
        } else {
            try {
                outputStream = new PrintStream(
                        Files.newOutputStream(Paths.get(filename), StandardOpenOption.CREATE, StandardOpenOption.APPEND),
                        false,
                        StandardCharsets.UTF_8.name());
            } catch (IOException e) {
                throw new ConnectException("Couldn't find or create file '" + filename + "' for FileStreamSinkTask", e);
            }
        }
//        connection = MysqlConnection.connectToDatabase();

        log.info("Starting file sink connector writing to {}", filename);
    }

    @Override
    public void put(Collection<SinkRecord> sinkRecords) {
        for (SinkRecord record : sinkRecords) {
            log.trace("Writing line to {}: {}", logFilename(), record.value());

            Struct struct = (Struct) record.value();
            Schema valueSchema =  record.valueSchema();
            if (valueSchema != null) {
//                String propertiesInfor[] = new String[4];
                String extractedData[] = new String[4];
                Personal personal = new Personal();
                personal.setFullname(new Fullname());
                personal.setInfo(new Info());
                for (Field field : valueSchema.fields()) {
//                    propertiesInfor[field.index()] = mappingProperties.getProperty(field.name());

                    extractedData[field.index()] = stringMapper.build(struct, field.name(), field.schema().type());
                    outputStream.println(extractedData[field.index()] + personal.getFieldName(field.name()));
                }
            }

        }
    }



    @Override
    public void flush(Map<TopicPartition, OffsetAndMetadata> offsets) {
        log.trace("Flushing output stream for {}", logFilename());
        outputStream.flush();
    }

    @Override
    public void stop() {
    }

    private String logFilename() {
        return filename == null ? "stdout" : filename;
    }

}
