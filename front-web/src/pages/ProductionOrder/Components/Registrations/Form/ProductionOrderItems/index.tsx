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
        .catch((error) => {
            toast.error(error.response.data.message)
        })
    }
    const onEditItem = (data: ProductionOrderItem) => {
        makePrivateRequest({
            url: '/productionorderitems/',
            method: 'PUT',
            data: data
        })
        .then(() => {
                toast.success("Item atualizado com sucesso!");
                getProductionOrder();
        })
        .catch((error) => {
            toast.error(error.response.data.message)
        })
    }
    const onDeleteItem = (data: ProductionOrderItem) => {
        const confirm = window.confirm("Deseja excluir a formulação selecionada?");
        if (confirm){
            makePrivateRequest({
                url: `/productionorderitems?productionOrderId=${data?.productionOrderId}
                      &productId=${data.product.id}&serie=${data.serie}`,
                method: 'DELETE',
            })
            .then(() => {
                    toast.success("Item excluido com sucesso!");
                    getProductionOrder();
            })
            .catch((error) => {
                toast.error(error.response.data.message)
            })
        }
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
                            onDeleteItem={onDeleteItem}
                            key={item.stockId}
                        />
                    ))
                )

            }



        </form>
    )
}

export default ProductionOrderItems;