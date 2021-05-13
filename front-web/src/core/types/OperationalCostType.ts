export type OperationalCostTypesResponse ={
    content: OperationalCostType[];
    totalPages: number;
}
export type OperationalCostType={
    id:number;
    name:string;
    type:string;
}