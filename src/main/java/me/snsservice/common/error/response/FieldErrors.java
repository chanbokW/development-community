package me.snsservice.common.error.response;

import lombok.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FieldErrors {

    private List<FieldError> fieldErrors;

    public FieldErrors(List<FieldError> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }

    public static FieldErrors of(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream()
                .map(FieldError::of)
                .collect(Collectors.collectingAndThen(Collectors.toList(), FieldErrors::new));
    }

    public static FieldErrors of(List<FieldError> fieldErrors) {
        return new FieldErrors(fieldErrors);
    }

    @Getter
    public static class FieldError {
        private String field;
        private String value;
        private String reason;

        @Builder
        private FieldError(String field, String value, String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        public static FieldError of(String field, String value, String reason) {
            return FieldError.builder()
                    .field(field)
                    .value(value)
                    .reason(reason)
                    .build();
        }

        public static FieldError of(org.springframework.validation.FieldError fieldError) {
            return FieldError.builder()
                    .field(fieldError.getField())
                    .value((fieldError.getRejectedValue() == null)? "" : fieldError.getRejectedValue().toString())
                    .reason(fieldError.getDefaultMessage())
                    .build();
        }
    }
}
