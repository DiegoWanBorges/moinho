import { ProductionOrder, ProductionOrderProduced } from 'core/types/ProductionOrder';
import { makePrivateRequest } from 'core/utils/request';
import { useCallback, useEffect, useState } from 'react';
import { toast } from 'react-toastify';
import ProductionOrderProducedCard from './ProductionOrderProducedCard';
import ProductionOrderProducedInsert from './ProductionOrderProducedInsert';
import './styles.scss';

type Props = {
    productionOrderId: string;
}

const ProductionOrderProduceds = ({ productionOrderId }: Props) => {
    const [productionOrder, setProductionOrder] = useState<ProductionOrder>();


    const getProductionOrder = useCallback(() => {
        makePrivateRequest({ url: `/productionorders/${productionOrderId}` })
            .then(response => setProductionOrder(response.data))
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
        .catch(() => {
                toast.error("Erro ao salvar produção!")
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
        .catch(() => {
                toast.error("Erro ao atualizar item!")
        })
    }
    const onDeleteItem = (data: ProductionOrderProduced) => {
        const confirm = window.confirm("Deseja excluir a formulação selecionada?");
        if (confirm){
            makePrivateRequest({
                url: `/productionordersproduced?productionOrderId=${data.productionOrderId}&pallet=${data.pallet}`,
                method: 'DELETE',
            })
            .then(() => {
                    toast.success("Item excluido com sucesso!");
                    getProductionOrder();
            })
            .catch(() => {
                    toast.error("Erro ao excluir item!")
            })
        }
    }
    return (
        <div>
            {
                productionOrder && (
                    <>
                    <ProductionOrderProducedInsert 
                        productionOrder={productionOrder}
                        onInsertItem={onInsertItem}
                    />

                    {productionOrder.productionOrderProduceds.map(item =>(
                        <ProductionOrderProducedCard 
                            productionOrderProduced={item}
                            onDeleteItem={onDeleteItem}
                            onEditItem={onEditItem}
                            formulationId={productionOrder.formulation.id}
                            key={item.stockId}
                        />
                    ))

                    }
                    </>
                )
               
            }
        </div>
    )
}

export default ProductionOrderProduceds