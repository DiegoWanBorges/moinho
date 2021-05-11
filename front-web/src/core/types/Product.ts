export type UnitysResponse ={
    content: Unity[];
    totalPages: number;
}
export type GroupsResponse ={
    content: Group[];
    totalPages: number;
}
export type ProductsResponse ={
    content: Product[];
    totalPages: number;
}

export type Unity={
    id:string;
    description:string;
}
export type Group={
    id:number;
    name:string;
}
export type Product={
    id: number;
    name: string;
    description: string;
    packaging:  string;
    netWeight: number;
    grossWeight: number;
    validityDays: number;
    costLastEntry: number;
    averageCost: number;
    rawMaterialConversion: number;
    stockBalance: number;
    unity: Unity;
    group: Group;
}