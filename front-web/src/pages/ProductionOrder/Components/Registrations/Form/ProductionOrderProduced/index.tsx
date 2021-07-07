import './styles.scss';

import { ProductionOrder, ProductionOrderProduced } from 'core/types/ProductionOrder';
import { makePrivateRequest } from 'core/utils/request';
import { useCallback, useEffect, useState } from 'react';
import { toast } from 'react-toastify';
import ProductionOrderProducedCard from './ProductionOrderProducedCard';
import ProductionOrderProducedInsert from './ProductionOrderProducedInsert';
import Loader from "react-loader-spinner";

type Props = {
    productionOrderId: string;
}

const ProductionOrderProduceds = ({ productionOrderId }: Props) => {
    const [productionOrder, setProductionOrder] = useState<ProductionOrder>();
    const [isLoading, setIsLoading] = useState(false);

    const getProductionOrder = useCallback(() => {
        setIsLoading(true)
        makePrivateRequest({ url: `/productionorders/${productionOrderId}` })
            .then(response => setProductionOrder(response.data))
            .finally(() => setIsLoading(false))
    }, [productionOrderId])

    useEffect(() => {
        getProductionOrder()
    }, [getProductionOrder])


    const onInsertItem = (data: ProductionOrderProduced) => {
        makePrivateRequest({
            url: '/productionordersproduced/',
            method: 'POST',
            data: data
        })
            .then(() => {
                toast.success("Produção salva com sucesso!");
                getProductionOrder();
            })
            .catch((error) => {
                toast.error(error.response.data.message)
            })
    }
    const onEditItem = (data: ProductionOrderProduced) => {
        makePrivateRequest({
            url: '/productionordersproduced/',
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
    const onDeleteItem = (data: ProductionOrderProduced) => {
        const confirm = window.confirm("Deseja excluir a formulação selecionada?");
        if (confirm) {
            makePrivateRequest({
                url: `/productionordersproduced?productionOrderId=${data.productionOrderId}&pallet=${data.pallet}`,
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
        <>
            {isLoading ?
                <div className="productionOrderProduced-loader">
                    <Loader
                        type="TailSpin"
                        height={100}
                        width={100}
                        color="#0670B8"
                    />
                </div> :
                (productionOrder && (
                    <>
                        <ProductionOrderProducedInsert
                            productionOrder={productionOrder}
                            onInsertItem={onInsertItem}
                        />
                        {productionOrder.productionOrderProduceds.map(item => (
                            <ProductionOrderProducedCard
                                productionOrderProduced={item}
                                onDeleteItem={onDeleteItem}
                                onEditItem={onEditItem}
                                formulationId={productionOrder.formulation.id}
                                key={item.stockId}
                            />
                        ))}
                    </>
                ))}
        </>
    )
}

export default ProductionOrderProduceds