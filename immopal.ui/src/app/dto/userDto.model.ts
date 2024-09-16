import { Role } from "../model/role.enum";

export interface UserDto {
  userId: number;
  email: string;
  role: Role;
  personId: number;
  accountNonExpired: boolean;
  accountNonLocked: boolean;
  credentialsNonExpired: boolean;
  enabled: boolean;
}
