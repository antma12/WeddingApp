import { Guest } from '../guests/Guest';

export interface MassFunction {
  id: number;
  name: string;
  guests: Guest[];
}
