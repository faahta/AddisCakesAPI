package et.addis.home_cakes.orders.mapper;

import et.addis.home_cakes.orders.dto.BusinessHoursDto;
import et.addis.home_cakes.orders.model.BusinessHours;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Created by Fassil on 16/02/22.
 */
@Mapper
public interface BusinessHoursMapper {
    BusinessHoursMapper INSTANCE = Mappers.getMapper(BusinessHoursMapper.class);
    @Mapping(source = "id", target = "id")
    BusinessHoursDto modelToDto(BusinessHours pastries);

    List<BusinessHoursDto> modelsToDtos(List<BusinessHours> businessHours);

    @InheritInverseConfiguration
    BusinessHours dtoToModel(BusinessHoursDto businessHoursDto);

    @InheritInverseConfiguration
    List<BusinessHours> dtosToModels(List<BusinessHoursDto> businessHoursDtos);
}
