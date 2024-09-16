import { UserDto } from "../dto/userDto.model";
import { Role } from "./role.enum";

export class User {
  constructor(
    public userId: number,
    public email: string,
    public role: Role,
    public personId: number,
    public accountNonExpired: boolean,
    public accountNonLocked: boolean,
    public credentialsNonExpired: boolean,
    public enabled: boolean,
    public password?: string,
  ) { }

  static fromDto(dto: UserDto): User {
    return new User(
      dto.userId,
      dto.email,
      dto.role,
      dto.personId,
      dto.accountNonExpired,
      dto.accountNonLocked,
      dto.credentialsNonExpired,
      dto.enabled
    );
  }
}
