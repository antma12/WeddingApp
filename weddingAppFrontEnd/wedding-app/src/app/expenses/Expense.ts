export interface Expense {
  id: number;
  type: string;
  name: string;
  minCost: number;
  maxCost: number;
  actualCost: number;
  splitCost: boolean;
}
