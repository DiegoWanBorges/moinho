import { Product } from "./Product";
import { Formulation } from "./Formulation";


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
        
// "productionOrderProduceds": [],
// "productionOrderCostLabor": [],
// "productionOrderOperationalCost": []
