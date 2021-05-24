import { Product } from "./Product";
import { Formulation } from "./Formulation";

export type ProductionOrdersResponse ={
    content: ProductionOrder[];
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

export type ProducedProductStatus={
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
        producedProductStatus:ProducedProductStatus;
        stockId:number;
}
// "productionOrderCostLabor": [],
// "productionOrderOperationalCost": []
