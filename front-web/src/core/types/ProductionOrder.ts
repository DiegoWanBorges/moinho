import { Product } from "./Product";
import { Formulation } from "./Formulation";
import { Sector } from "./Employee";
import { OperationalCostType } from "./Payment";

export type ProductionOrdersResponse ={
    content: ProductionOrder[];
    totalPages: number;
}
export type OccurrencesResponse ={
    content: Occurrence[];
    totalPages: number;
}
export type PalletstatusResponse ={
    content: Palletstatus[];
    totalPages: number;
}
export type ProductionOrder= {
    id: number;
    emission: string;
    startDate: string;
    endDate: string;
    expectedAmount: number;
    observation: string;
    status: string;
    formulation: Formulation;
    productionOrderItems:ProductionOrderItem[];
    productionOrderProduceds:ProductionOrderProduced[];
    productionOrderCostLabor:ProductionOrderCostLabor[];
    productionOrderOperationalCost:ProductionOrderOperationalCost[];
    totalProduced: number;
    totalDirectCost: number;
    unitCost: number;
    totalIndirectCost: number;
}
    
export type ProductionOrderItem= {
            stockId: number;
            serie: number;
            quantity: number;
            cost: number;
            type: string;
            rawMaterial: boolean;
            productionOrderId: number;
            product: Product;
            occurrence: Occurrence;
        }

export type Occurrence={
    id:number;
    name:string;
}

export type Palletstatus={
    id:number;
    name:string;
}
export type ProductionOrderProduced ={
        productionOrderId: number;
        pallet: number;
        manufacturingDate: string;
        lote: string;
        quantity: number;
        product:Product;
        palletStatus:Palletstatus;
        stockId:number;
}
export type ProductionOrderCostLabor ={
    productionOrderId: number;
    sector: Sector;
    value: number;
}
export type ProductionOrderOperationalCost ={
     productionOrderId:number;
     operationalCostType:OperationalCostType;
     value:number;
}
