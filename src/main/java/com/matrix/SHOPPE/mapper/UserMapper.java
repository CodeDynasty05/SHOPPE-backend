package com.matrix.SHOPPE.mapper;

import com.matrix.SHOPPE.model.DTO.UserAddRequestDTO;
import com.matrix.SHOPPE.model.DTO.UserDTO;
import com.matrix.SHOPPE.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "phone",target = "phone",qualifiedByName = "hidePhoneNumber")
    UserDTO toUserDTO(User user);

    @Named("hidePhoneNumber")
    default String hidePhoneNumber(String phone) {
        String ending =phone.substring(phone.length()-4);
        String starting =phone.substring(0, phone.length()-4);
        return starting.replaceAll("[0-9+]","*")+ending;
    }
    @Mapping(source = "password",target = "passwordHash",qualifiedByName = "hash")
    User toUser(UserAddRequestDTO userAddRequestDTO);

    @Named("hash")
    default String hash(String password) {
        //To Do: add hashing algorithm
        return password;
    }
}

