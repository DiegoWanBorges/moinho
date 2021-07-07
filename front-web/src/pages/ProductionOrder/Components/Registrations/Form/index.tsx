import './styles.scss';

import { ProductionOrder } from 'core/types/ProductionOrder';
import { makePrivateRequest } from 'core/utils/request';
import { useEffect, useState } from 'react';
import { useParams } from 'react-router';
import { Tab, Tabs, TabList, TabPanel } from 'react-tabs';
import ProductionOrderHeader from './ProductionOrderHeader';
import ProductionOrderItems from './ProductionOrderItems';
import ProductionOrderProduceds from './ProductionOrderProduced';
import Loader from "react-loader-spinner";

type ParamsType = {
    productionOrderId: string;
}

function ProductionOrderForm() {
    const { productionOrderId } = useParams<ParamsType>();
    const [productionOrder, setProductionOrder] = useState<ProductionOrder>();
    const [isLoading, setIsLoading] = useState(false);

    useEffect(() => {
        setIsLoading(true)
        makePrivateRequest({ url: `/productionorders/${productionOrderId}` })
            .then(response => setProductionOrder(response.data))
            .finally(() => setIsLoading(false))
    }, [productionOrderId])

    return (
        <form >

            <Tabs className="tab-main">
                <TabList>
                    <Tab>Ordem de Produção</Tab>
                    <Tab>Ingredientes</Tab>
                    <Tab>Produção</Tab>
                </TabList>

                <TabPanel>
                    {isLoading ?
                        <div className="productionOrder-tab-header">
                            <Loader
                                type="TailSpin"
                                height={100}
                                width={100}
                                color="#0670B8" 
                            />
                        </div> :
                        (productionOrder && (
                            <ProductionOrderHeader
                                productionOrder={productionOrder}
                            />
                        ))}
                </TabPanel>

                <TabPanel>
                    <ProductionOrderItems
                        productionOrderId={productionOrderId}
                    />
                </TabPanel>

                <TabPanel>
                    <ProductionOrderProduceds
                        productionOrderId={productionOrderId}
                    />
                </TabPanel>





            </Tabs>
        </form>
    );
}
export default ProductionOrderForm;