package ubb.graduation24.immopal.security_service.mapper;

import org.mapstruct.Mapper;
import ubb.graduation24.immopal.security_service.domain.User;
import ubb.graduation24.immopal.security_service.model.UserDto;

@Mapper
public interface UserMapper {

    UserDto userToUserDto(User user);

    User userDtoToUser(UserDto userDto);

}
