import { ProductionOrder, ProductionOrderItem } from 'core/types/ProductionOrder';
import { makePrivateRequest } from 'core/utils/request';
import { useCallback, useEffect, useState } from 'react';
import { toast } from 'react-toastify';

import ProductionOrderItemCard from './ProductionOrderItemCard';
import ProductionOrderItemsInsert from './ProductionOrderItemsInsert';
import './styles.scss';

type Props = {
    productionOrderId: string;
}

const ProductionOrderItems = ({ productionOrderId }: Props) => {
    const [productionOrder, setProductionOrder] = useState<ProductionOrder>();


    const getProductionOrder = useCallback(() => {
        makePrivateRequest({ url: `/productionorders/${productionOrderId}` })
            .then(response => setProductionOrder(response.data))
    }, [productionOrderId])

    useEffect(() => {
        getProductionOrder()
    }, [getProductionOrder])


    const onInsertItem = (data: ProductionOrderItem) => {
        makePrivateRequest({
            url: '/productionorderitems/',
            method: 'POST',
            data: data
        })
        .then(() => {
                toast.success("Item salvo com sucesso!");
                getProductionOrder();
        })
        .catch(() => {
                toast.error("Erro ao salvar item!")
        })
    }
    const onEditItem = (data: ProductionOrderItem) => {
        console.log(data)
        makePrivateRequest({
            url: '/productionorderitems/',
            method: 'PUT',
            data: data
        })
        .then(() => {
                toast.success("Item atualizado com sucesso!");
                getProductionOrder();
        })
        .catch(() => {
                toast.error("Erro ao atualizar item!")
        })
    }

    return (
        <form>
            {
                productionOrder && (
                    <ProductionOrderItemsInsert
                        onInsertItem={onInsertItem}
                        productionOrder={productionOrder}
                    />
                )
            }


            {
                productionOrder && (
                    productionOrder.productionOrderItems.map(item => (
                        <ProductionOrderItemCard
                            productionOrderItem={item}
                            onEditItem={onEditItem}
                        />
                    ))
                )

            }



        </form>
    )
}

export default ProductionOrderItems;