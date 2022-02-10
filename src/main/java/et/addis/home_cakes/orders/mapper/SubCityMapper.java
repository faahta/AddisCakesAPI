package et.addis.home_cakes.orders.mapper;

import et.addis.home_cakes.orders.dto.PastryDto;
import et.addis.home_cakes.orders.dto.SubCityDto;
import et.addis.home_cakes.orders.model.Pastry;
import et.addis.home_cakes.orders.model.SubCity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Created by Fassil on 04/02/22.
 */
@Mapper(componentModel = "spring")
public interface SubCityMapper {
    SubCityMapper INSTANCE = Mappers.getMapper(SubCityMapper.class);

    @Mapping(source = "id", target = "id")
    SubCityDto modelToDto(SubCity subCity);
    List<SubCityDto> modelsToDtos(List<SubCity> subCities);

    @InheritInverseConfiguration
    SubCity dtoToModel(SubCityDto pastryDto);
}
