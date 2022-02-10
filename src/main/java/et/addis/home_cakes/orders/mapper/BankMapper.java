package et.addis.home_cakes.orders.mapper;

import et.addis.home_cakes.orders.dto.BankDto;
import et.addis.home_cakes.orders.dto.PastryDto;
import et.addis.home_cakes.orders.model.Bank;
import et.addis.home_cakes.orders.model.Pastry;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Created by Fassil on 02/02/22.
 */
@Mapper(componentModel = "spring")
public interface BankMapper {
    BankMapper INSTANCE = Mappers.getMapper(BankMapper.class);

    @Mapping(source = "bankId", target = "bankId")
    BankDto modelToDto(Bank bank);
    List<BankDto> modelsToDtos(List<Bank> banks);

    @InheritInverseConfiguration
    Bank dtoToModel(BankDto pastryDto);
}
