package logisticsapp.exceptions;

public record ValidationError(String field, String error) {
}
