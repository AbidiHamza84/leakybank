package fr.ing.secu.leakybank.domain.administration;

import lombok.*;
import org.jqassistant.contrib.plugin.ddd.annotation.DDD;

@DDD.ValueObject
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SqlQuery {
    @NonNull
    String sqlQuery;
}
