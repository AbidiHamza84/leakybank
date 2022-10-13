package fr.ing.secu.leakybank.domain.administration;

import lombok.*;
import org.jqassistant.contrib.plugin.ddd.annotation.DDD;

import java.util.ArrayList;
import java.util.List;

@DDD.ValueObject
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class SqlResult {
    @Builder.Default
    List<String> columnsNames = new ArrayList<>();
    @Builder.Default
    List<List<String>> resultSet = new ArrayList<>();
}
