import { IBookingSlot } from 'app/shared/model/booking-slot.model';

export interface INewUser {
  id?: number;
  userId?: string;
  name?: string;
  phoneNumber?: string;
  bookingSlots?: IBookingSlot[];
}

export class NewUser implements INewUser {
  constructor(
    public id?: number,
    public userId?: string,
    public name?: string,
    public phoneNumber?: string,
    public bookingSlots?: IBookingSlot[]
  ) {}
}
