import { ProductionOrder } from 'core/types/ProductionOrder';
import { makePrivateRequest } from 'core/utils/request';
import { useEffect, useState } from 'react';
import { useParams } from 'react-router';
import { Tab, Tabs, TabList, TabPanel } from 'react-tabs';
import ProductionOrderHeader from './ProductionOrderHeader';
import ProductionOrderItems from './ProductionOrderItems';

import './styles.scss';

type ParamsType = {
    productionOrderId: string;
}

function ProductionOrderForm() {
    const { productionOrderId } = useParams<ParamsType>();
    const [productionOrder, setProductionOrder] = useState<ProductionOrder>();

    useEffect(() => {
        makePrivateRequest({ url: `/productionorders/${productionOrderId}` })
            .then(response => setProductionOrder(response.data))
            .finally(() => {

            })
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
                    {productionOrder && (
                        <ProductionOrderHeader
                            productionOrder={productionOrder}
                        />
                    )}
                </TabPanel>
                <TabPanel>
                    {productionOrder && (
                        <ProductionOrderItems
                            productionOrderId={productionOrderId}
                        />
                    )}
                </TabPanel>





            </Tabs>
        </form>
    );
}
export default ProductionOrderForm;