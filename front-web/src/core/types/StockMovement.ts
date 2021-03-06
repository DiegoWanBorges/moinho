import { type } from "node:os";
import { Product } from "./Product";

export type StockMovementsResponse ={
    content: StockMovement[];
    totalPages: number;
}
export type StockMovement={
    id:number;
    date:string;
    entry:number;
    out:number;
    cost:number;
    description:string;
    type:string;
    idOrignMovement:number;
    product:Product;
}

export type StockBalance ={
    id: number;
    name: string;
    unity: string;
    balance: number;
    averageCost: number,
    totalEntry: number;
    totalOut: number;
}

