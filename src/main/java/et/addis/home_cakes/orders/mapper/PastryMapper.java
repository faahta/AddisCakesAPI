package et.addis.home_cakes.orders.mapper;

import et.addis.home_cakes.orders.dto.PastryDto;
import et.addis.home_cakes.orders.dto.UsersDto;
import et.addis.home_cakes.orders.model.Pastry;
import et.addis.home_cakes.orders.model.Users;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Created by Fassil on 27/01/22.
 */
@Mapper(componentModel = "spring")
public interface PastryMapper {

    PastryMapper INSTANCE = Mappers.getMapper(PastryMapper.class);

    @Mapping(source = "pastry.id", target = "id")
    PastryDto modelToDto(Pastry users);
    List<PastryDto> modelsToDtos(List<Pastry> users);

    @InheritInverseConfiguration
    Pastry dtoToModel(PastryDto pastryDto);
}
