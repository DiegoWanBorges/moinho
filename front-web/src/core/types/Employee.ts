export type SectorsResponse ={
    content: Sector[];
    totalPages: number;
}
export type EmployeesResponse ={
    content: Employee[];
    totalPages: number;
}
export type Sector={
    id:number;
    name:string;
}

export type Employee={
    id:number;
    name:string;
    sector:Sector
}