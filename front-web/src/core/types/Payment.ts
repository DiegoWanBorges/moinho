import { Employee } from "./Employee"

export type LaborCosTypesResponse ={
    content: LaborCosType[];
    totalPages: number;
}
export type LaborCosType ={
    id:number;
    name:string;
}

export type OperationalCostTypesResponse ={
    content: OperationalCostType[];
    totalPages: number;
}



export type OperationalCostType={
    id:number;
    name:string;
    type:string;
}


export type OperationalPaymentsResponse ={
    content: OperationalPayment[];
    totalPages: number;
}
export type OperationalPayment ={
    id: number;
    date: string;
    description: string;
    documentNumber: string;
    value: number;
    operationalCostType: OperationalCostType;
}

export type LaborPaymentsResponse ={
    content: LaborPayment[];
    totalPages: number;
}
export type LaborPayment ={
    id: number;
    date: string;
    description: string;
    documentNumber: string;
    value: number;
    employee:Employee;
    laborCostType:LaborCosType;
}