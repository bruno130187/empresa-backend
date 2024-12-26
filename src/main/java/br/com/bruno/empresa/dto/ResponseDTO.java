package br.com.bruno.empresa.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO {

    private List<String> messages;
    private Object empresa;

    public ResponseDTO(String message) {
        this.messages = Collections.singletonList(message);
    }

    public ResponseDTO(String message, Object data) {
        this.messages = Collections.singletonList(message);
        this.empresa = data;
    }

}
