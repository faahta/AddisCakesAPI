package et.addis.home_cakes.pastries.mapper;

import et.addis.home_cakes.pastries.dto.UsersDto;
import et.addis.home_cakes.pastries.model.Users;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Created by Fassil on 11/01/22.
 */

@Mapper(componentModel = "spring")
public interface UsersMapper {

    UsersMapper INSTANCE = Mappers.getMapper(UsersMapper.class);
    @Mapping(source = "users.userId", target = "userId")
    UsersDto modelToDto(Users users);
    List<UsersDto> modelsToDtos(List<Users> users);

    @InheritInverseConfiguration
    Users dtoToModel(UsersDto usersDto);

}
