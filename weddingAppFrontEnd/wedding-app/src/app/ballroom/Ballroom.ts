export interface Ballroom {
  id: number;
  name: string;
  city: string;
  distance: number;
  availableDates: Date[];
  price: number;
  pricePerNight: number;
  isWithNightStay: boolean;
  opinion: string;
}
