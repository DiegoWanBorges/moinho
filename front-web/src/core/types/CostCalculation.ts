import { ProductionOrder } from "./ProductionOrder"

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
export type CostCalculationResult ={
    costCalculation:CostCalculation;
    productionOrders:ProductionOrder[];
    openingStockBalance:StockBalance[];
    closingStockBalance:StockBalance[];
    purchaseStockBalance:StockBalance[];
    adjustmentStockBalance:StockBalance[];
    productionOrderProducedAverageCosts:ProductionOrderProducedAverageCost[];
}

export type StockBalance ={
    id: number;
    name: string;
    unity: string;
    balance: number;
    averageCost: number;
}
export type ProductionOrderProducedAverageCost ={
    id: number;
    name: string;
    unity: string;
    totalProduced: number;
    averageCost: number;
}