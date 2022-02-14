package et.addis.home_cakes.orders.mapper;

import et.addis.home_cakes.orders.dto.BranchDto;
import et.addis.home_cakes.orders.dto.PastryDto;
import et.addis.home_cakes.orders.model.Branch;
import et.addis.home_cakes.orders.model.Pastry;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Created by Fassil on 11/02/22.
 */
@Mapper
public interface BranchMapper {

    BranchMapper INSTANCE = Mappers.getMapper(BranchMapper.class);
    @Mapping(source = "branchId", target = "branchId")
    BranchDto modelToDto(Branch branches);

    @InheritInverseConfiguration
    Branch dtoToModel(BranchDto branch);

    List<BranchDto> modelsToDtos(List<Branch> branches);
    List<Branch> dtosToModels(List<BranchDto> branches);

}
