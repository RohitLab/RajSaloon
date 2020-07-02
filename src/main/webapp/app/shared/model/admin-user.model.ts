export interface IAdminUser {
  id?: number;
  userId?: string;
  name?: string;
  phoneNumber?: string;
  encryptedPassword?: string;
}

export class AdminUser implements IAdminUser {
  constructor(
    public id?: number,
    public userId?: string,
    public name?: string,
    public phoneNumber?: string,
    public encryptedPassword?: string
  ) {}
}
