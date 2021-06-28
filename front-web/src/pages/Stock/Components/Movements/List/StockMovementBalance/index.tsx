import { Product } from 'core/types/Product';
import { StockBalance } from 'core/types/StockMovement';
import { makePrivateRequest } from 'core/utils/request';
import { useEffect, useState } from 'react';
import './styles.scss'

type Props = {
    product: Product;
    startDate: string;
    endDate: string;
}
const StockMovementBalance = ({ product, startDate, endDate }: Props) => {
    const [stockStart, setStockStart] = useState<StockBalance>();
    const [stockMovement, setStockMovement] = useState<StockBalance>();
    const [stockEnd, setStockEnd] = useState<StockBalance>();

    useEffect(() => {
        makePrivateRequest({ url: `/stocks?stockByPreviousDateAndProductId=${product.id}&date=${startDate}` })
            .then(response => {
                setStockStart(response.data)
            })
    }, [product, startDate])

    useEffect(() => {
        makePrivateRequest({ url: `/stocks?stockByDateBetweenAndProductId=${product.id}&startDate=${startDate}&endDate=${endDate}` })
            .then(response => {
                setStockMovement(response.data)
            })
    }, [product, startDate, endDate])

    useEffect(() => {
        makePrivateRequest({ url: `/stocks?stockByPreviousAndEqualDateAndProductId=${product.id}&date=${endDate}` })
            .then(response => {
                setStockEnd(response.data)
            })
    }, [product, endDate])

    return (
        <div className="stockMovementBalance-main">
            <div className="stockMovementBalance-initialStock">
                <h6>Estoque Inicial</h6>
                <div className="stockMovementBalance-initialStock-inf">
                    <text>{`Saldo Inicial: ${stockStart?.balance} ${product?.unity.id}`}</text>
                    <text>Custo Médio: {stockStart?.averageCost}</text>

                </div>
            </div>
            <div className="stockMovementBalance-movement">
                <h6>Movimentação</h6>
                <div className="stockMovementBalance-movement-inf">
                    <text>{`Entrada: ${stockMovement?.totalEntry} | Saida: ${stockMovement?.totalOut} ${product.unity.id}`}</text>
                    <text>{`Custo Médio: ${stockMovement?.averageCost}`}</text>
                </div>
            </div>
            <div className="stockMovementBalance-finalStock">
                <h6>Estoque Final</h6>
                <div className="stockMovementBalance-finalStock-inf">
                    <text>{`Saldo Final: ${stockEnd?.balance} ${product?.unity.id}`}</text>
                    <text>Custo Médio: {stockEnd?.averageCost}</text>
                </div>
            </div>
        </div>
    )
}
export default StockMovementBalance;
