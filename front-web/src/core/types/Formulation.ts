import { Sector } from "./Employee";
import { OperationalCostType } from "./Payment";
import { Product } from "./Product";

export type FormulationResponse ={
    content: Formulation[];
    totalPages: number;
}


export type Formulation={
    id: number;
    coefficient: number
    description:string
    product: Product;
    type:string;
    level:number;
    operationalCostType: OperationalCostType[];
    sectors: Sector[];
    formulationItems:FormulationItem[];
    secondaryProduction:Product[];
}

export type FormulationItem ={
    formulationId: number;
    quantity: number;
    round: boolean;
    rawMaterial: boolean;
    type: string ;
    product:Product;
}