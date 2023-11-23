package wi.kafka.sink;

import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.Struct;

public class StringMapper {

    public String build(Struct struct, String name, Schema.Type type) {
        String extractedData;

        switch (type) {
            case INT8:
            case INT16:
                extractedData = String.format("%d", struct.getInt16(name));
                break;
            case INT32:
                extractedData = String.format("%d", struct.getInt32(name));
                break;
            case INT64:
                extractedData = String.format("%d", struct.getInt64(name));
                break;
            case FLOAT32:
                extractedData = String.format("%.2f", struct.getFloat32(name));
                break;
            case FLOAT64:
                extractedData = String.format("%.2f", struct.getFloat64(name));
                break;
            case BOOLEAN:
                extractedData = String.format("%b", struct.getBoolean(name));
                break;
            case STRING:
                extractedData = String.format("%s", struct.getString(name));
                break;
            default:

                extractedData = "Unsupported type";
        }

        return extractedData;
    }



}
