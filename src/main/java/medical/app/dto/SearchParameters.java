package medical.app.dto;

public record SearchParameters(
        String search,
        Integer[] doctorsId
) {
}
