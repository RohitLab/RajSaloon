import { Moment } from 'moment';

export interface IBookingSlot {
  id?: number;
  bookingId?: number;
  description?: string;
  bookingDate?: Moment;
  slotStarting?: Moment;
  slotEnding?: Moment;
  newUserId?: number;
}

export class BookingSlot implements IBookingSlot {
  constructor(
    public id?: number,
    public bookingId?: number,
    public description?: string,
    public bookingDate?: Moment,
    public slotStarting?: Moment,
    public slotEnding?: Moment,
    public newUserId?: number
  ) {}
}
