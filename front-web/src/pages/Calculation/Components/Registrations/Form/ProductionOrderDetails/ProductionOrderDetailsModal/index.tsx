import { ProductionOrder } from 'core/types/ProductionOrder';
import './styles.scss';
import Modal from 'react-modal';
import { useState } from 'react';
import { Tab, TabList, TabPanel, Tabs } from 'react-tabs';
import ProductionOrderDetailsInputs from './ProductionOrderDetailsInputs'
import ProductionOrderDetailsOperationalCost from './ProductionOrderDetailsOperationalCost'
import ProductionOrderDetailsCostLabor from './ProductionOrderDetailsCostLabor'
import ProductionOrderDetailsProduced from './ProductionOrderDetailsProduced'
import { formatPrice } from 'core/utils/utils';
type Props = {
    productionOrder: ProductionOrder;
}

const CostCalculationProductionOrderDetailsModal = ({ productionOrder }: Props) => {
    const [show, setShow] = useState(false);
    const onEdit = (e: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
        e.preventDefault();
        setShow(!show)
    }
    async function afterOpenModal() {

    }
    return (
        <>
            <button
                className="btn btn-outline-secondary"
                onClick={e => onEdit(e)}
            >
                VISUALIZAR
            </button>

            <Modal
                isOpen={show}
                onAfterOpen={afterOpenModal}
                ariaHideApp={false}
                className="productionOrderDetailsModal-main"
                overlayClassName="productionOrderDetailsModal-overlay"
            >
                <Tabs className="tab-main">
                    <TabList>
                        <Tab>Insumos</Tab>
                        <Tab>Custos Operacionais</Tab>
                        <Tab>Custos Mão de Obra</Tab>
                        <Tab>Produzido</Tab>
                    </TabList>

                    <TabPanel>
                        <div className="productionOrderDetailsModal-inputs">
                            <h6>Total Insumos: {formatPrice(productionOrder.totalDirectCost)}</h6>
                        </div>
                        {productionOrder.productionOrderItems.map(item => (
                            <ProductionOrderDetailsInputs
                                key={item.stockId}
                                productionOrderItem={item}
                            />
                        ))}
                    </TabPanel>
                    <TabPanel>
                        {productionOrder.productionOrderOperationalCost.map(item => (
                            <ProductionOrderDetailsOperationalCost
                                key={item.operationalCostType.id}
                                productionOrderOperationalCost={item}
                            />
                        ))}
                    </TabPanel>
                    <TabPanel>
                        {productionOrder.productionOrderCostLabor.map(item => (
                            <ProductionOrderDetailsCostLabor
                                key={item.sector.id}
                                productionOrderCostLabor={item}
                            />
                        ))}
                    </TabPanel>
                    <TabPanel>
                        {productionOrder.productionOrderProduceds.map(item => (
                            <ProductionOrderDetailsProduced
                                key={item.pallet}
                                productionOrderProduced={item}
                            />
                        ))}
                    </TabPanel>
                </Tabs>

                <div className="productionOrderDetailsModal-actions">
                    <button
                        className="btn btn-secondary"
                        onClick={() => setShow(!show)}
                    >
                        Voltar
                    </button>
                </div>

            </Modal>
        </>
    )
}

export default CostCalculationProductionOrderDetailsModal;