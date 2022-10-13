package fr.ing.secu.leakybank.application.pages.admin.mapper;

import fr.ing.secu.leakybank.application.pages.admin.SQLConsoleForm;
import fr.ing.secu.leakybank.application.pages.login.UserDTO;
import fr.ing.secu.leakybank.domain.administration.SqlQuery;
import fr.ing.secu.leakybank.domain.user.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper
@MapperConfig(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AdminMapper {
    AdminMapper INSTANCE = Mappers.getMapper( AdminMapper.class );
    
    SqlQuery toDomain(SQLConsoleForm sqlConsoleForm);
}