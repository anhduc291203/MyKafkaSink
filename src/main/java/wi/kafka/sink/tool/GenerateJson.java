package wi.kafka.sink.tool;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class GenerateJson {
    public String generateStringJson() {
        String[][] data = ReadExcelFile.getDataFromExcel();

        String json = convertToJson(data);

        System.out.println(json);
        return json;
    }

    public static String convertToJson(String[][] data) {
        Gson gson = new Gson();

        JsonObject jsonObject = new JsonObject();

        for (int i = 0; i < data.length; i++) {
            jsonObject.addProperty(data[i][0], data[i][2]);
        }

        return gson.toJson(jsonObject);
    }
}
