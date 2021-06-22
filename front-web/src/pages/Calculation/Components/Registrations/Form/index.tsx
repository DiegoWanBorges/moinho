import StockBalanceCard from 'core/components/StockBalance';
import { CostCalculationResult } from 'core/types/CostCalculation';
import { makePrivateRequest } from 'core/utils/request';
import { toISOFormatDateTime } from 'core/utils/utils';
import moment from 'moment';
import { useEffect, useState } from 'react';
import { useParams } from 'react-router';
import { Tab, TabList, TabPanel, Tabs } from 'react-tabs';
import AverageCostProduction from './AverageCostProduction';
import CostCalculationProductionOrderDetails from './ProductionOrderDetails';
import './styles.scss';
import CostCalculationSummary from './Summary';


type ParamsType = {
    costCalculationId: string;
}
function CostCalculationForm() {
    const [costCalculationResult, setCostCalculationResult] = useState<CostCalculationResult>();

    const { costCalculationId } = useParams<ParamsType>();

    useEffect(() => {
        makePrivateRequest({ url: `/costcalculations/${costCalculationId}` })
            .then(response => setCostCalculationResult(response.data))
            .finally(() => {

            })
    }, [costCalculationId])


    return (
        <div className="costCalculation-main">
            <Tabs className="tab-main">
                <TabList>
                    <Tab>Resumo</Tab>
                    <Tab>Estoque Inicial</Tab>
                    <Tab>Compras</Tab>
                    <Tab>Ajuste de Estoque</Tab>
                    <Tab>Produção Detalhado</Tab>
                    <Tab>Custo Médio Produção</Tab>
                    <Tab>Estoque Final</Tab>
                </TabList>

                <TabPanel>
                    {costCalculationResult && (
                        <CostCalculationSummary
                            costCalculation={costCalculationResult.costCalculation}
                        />
                    )}
                </TabPanel>
                <TabPanel>
                    <div className="costCalculation-stockStart">
                        <h6 >Data: {costCalculationResult?.costCalculation.stockStartDate}</h6>
                    </div>
                    {costCalculationResult && (
                        costCalculationResult.openingStockBalance.map(item => (
                            <StockBalanceCard
                                key={item.id}
                                stockBalance={item}
                            />
                        ))
                    )}
                </TabPanel>
                <TabPanel>
                    {costCalculationResult && (
                        costCalculationResult.purchaseStockBalance.map(item => (
                            <StockBalanceCard
                                key={item.id}
                                stockBalance={item}
                            />
                        ))
                    )}
                </TabPanel>
                <TabPanel>
                    {costCalculationResult && (
                        costCalculationResult.adjustmentStockBalance.map(item => (
                            <StockBalanceCard
                                key={item.id}
                                stockBalance={item}
                            />
                        ))
                    )}
                </TabPanel>
                <TabPanel>
                    {costCalculationResult && (
                        costCalculationResult.productionOrders.map(item => (
                            <CostCalculationProductionOrderDetails
                                key={item.id}
                                productionOrder={item}
                            />
                        ))
                    )}
                </TabPanel>
                <TabPanel>
                    {costCalculationResult && (
                        costCalculationResult.productionOrderProducedAverageCosts.map(item => (
                            <AverageCostProduction
                                key={item.id}
                                averageCostProduction={item}
                            />
                        ))
                    )}
                </TabPanel>
                <TabPanel>
                    {costCalculationResult && (
                        <div className="costCalculation-stockStart">
                            <h6 >Data: {moment(toISOFormatDateTime(costCalculationResult.costCalculation.endDate)).format("DD/MM/YYYY")}</h6>
                        </div>
                    )}

                    {costCalculationResult && (
                        costCalculationResult.closingStockBalance.map(item => (
                            <StockBalanceCard
                                key={item.id}
                                stockBalance={item}
                            />
                        ))
                    )}

                </TabPanel>
            </Tabs>


        </div>
    );
}
export default CostCalculationForm;
