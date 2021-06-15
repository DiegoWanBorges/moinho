export type CostCalculationsResponse ={
    content: CostCalculation[];
    totalPages: number;
}
export type CostCalculation ={
    id: number;
    startDate: string;
    endDate:  string;
    stockStartDate:  string;
    status:  string;
    referenceMonth:string
}
