package et.addis.home_cakes.orders.mapper;

import et.addis.home_cakes.orders.dto.PastryDto;
import et.addis.home_cakes.orders.model.Pastry;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

/**
 * Created by Fassil on 27/01/22.
 */
@Mapper
public interface PastryMapper {

    PastryMapper INSTANCE = Mappers.getMapper(PastryMapper.class);

    @Mapping(source = "logoImage", target = "logoImage",  qualifiedByName = "bytesToString" )
    @Mapping(source = "image1", target = "image1",  qualifiedByName = "bytesToString" )
    @Mapping(source = "image2", target = "image2",  qualifiedByName = "bytesToString" )
    @Mapping(source = "image3", target = "image3",  qualifiedByName = "bytesToString" )
    PastryDto modelToDto(Pastry pastries);

    List<PastryDto> modelsToDtos(List<Pastry> pastries);

    //@InheritInverseConfiguration
    @Mapping(source = "logoImage", target = "logoImage", qualifiedByName = "stringToBytes")
    @Mapping(source = "image1", target = "image1",  qualifiedByName = "stringToBytes" )
    @Mapping(source = "image2", target = "image2",  qualifiedByName = "stringToBytes" )
    @Mapping(source = "image3", target = "image3",  qualifiedByName = "stringToBytes" )
    Pastry dtoToModel(PastryDto pastryDto);

    @Named(value = "stringToBytes")
    default byte[] map(String logoImage) throws UnsupportedEncodingException {
        if(logoImage == null) return null;
        try  {
            byte[] logo = Base64.getEncoder().encode(logoImage.getBytes());
            byte[] decodedImage = Base64.getDecoder().decode(new String(logo).getBytes("UTF-8"));
            return Objects.requireNonNull(decodedImage) ;
        } catch (UnsupportedEncodingException e) {
            System.out.println(e.getStackTrace());
            throw e;
        }
    }

    @Named(value = "bytesToString")
    default String map(byte[] logoImage)  {
        if(logoImage == null) return null;
        return new String(logoImage);
    }

}
