package com.matrix.SHOPPE.mapper;

import com.matrix.SHOPPE.model.dto.UserAddRequestDto;
import com.matrix.SHOPPE.model.dto.UserDto;
import com.matrix.SHOPPE.model.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "phone",target = "phone",qualifiedByName = "hidePhoneNumber")
    UserDto toUserDTO(User user);

    @Named("hidePhoneNumber")
    default String hidePhoneNumber(String phone) {
        String ending =phone.substring(phone.length()-4);
        String starting =phone.substring(0, phone.length()-4);
        return starting.replaceAll("[0-9+]","*")+ending;
    }

    User toUser(UserAddRequestDto userAddRequestDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User updateUser(UserAddRequestDto userAddRequestDTO, @MappingTarget User user);
}

