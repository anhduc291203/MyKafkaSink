package wi.kafka.sink.jsonschema;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.Generated;
import javax.validation.Valid;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "fullname",
        "info"
})
@Generated("jsonschema2pojo")
public class Personal {

    @JsonProperty("fullname")
    @Valid
    private Fullname fullname;
    @JsonProperty("info")
    @Valid
    private Info info;
    @JsonIgnore
    @Valid
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("fullname")
    public Fullname getFullname() {
        return fullname;
    }

    @JsonProperty("fullname")
    public void setFullname(Fullname fullname) {
        this.fullname = fullname;
    }

    @JsonProperty("info")
    public Info getInfo() {
        return info;
    }

    @JsonProperty("info")
    public void setInfo(Info info) {
        this.info = info;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

//    public void checkFieldName(String fieldName) {
//        Field[] fields = this.getClass().getDeclaredFields();
//        for (Field field : fields) {
//            field.setAccessible(true);
//            System.out.println(field.getName());
//        }
//        if (hasField(fieldName, fullname)) {
//            System.out.println(fieldName + " exists in " + fullname.getClass().getName());
//        }
//
//        if (hasField(fieldName, info)) {
//            System.out.println(fieldName + " exists in " + info.getClass().getName());
//        }
//    }
//
//    private boolean hasField(String fieldName, Object object) {
//        if (object == null) {
//            return false;
//        }
//
//        Class<?> clazz = object.getClass();
//        Field[] fields = clazz.getDeclaredFields();
//
//        for (Field field : fields) {
//            field.setAccessible(true);
//            JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
//            if (jsonProperty != null && jsonProperty.value().equals(fieldName)) {
//                return true;
//            }
//        }
//
//        return false;
//    }

    public String getFieldName(String fieldName) {

        this.setFullname(new Fullname());
        this.setInfo(new Info());
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getName() == "additionalProperties"){
                continue;
            }
            Object fieldValue;
            try {
                fieldValue = field.get(this);
                if (fieldValue != null && hasField(fieldName, fieldValue)) {
                    return fieldName + " exists in " + fieldValue.getClass().getName();
                }
            } catch (IllegalAccessException e) {
                // Handle the exception as needed
                e.printStackTrace();
            }
        }
        return fieldName + " not exists in this class";
    }

    private boolean hasField(String fieldName, Object object) {
        if (object == null) {
            return false;
        }

        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
            if (jsonProperty != null && jsonProperty.value().equals(fieldName)) {
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        Personal personal = new Personal();

        String fieldNameToCheck = "he"; // Thay đổi giá trị cần kiểm tra tại đây
        System.out.println(personal.getFieldName(fieldNameToCheck));
    }

}