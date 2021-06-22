
import { ProductionOrder } from 'core/types/ProductionOrder';
import './styles.scss';

type Props = {
    productionOrder: ProductionOrder;
}

const CostCalculationProductionOrderDetails = ({ productionOrder }: Props) => {
    return (
        <div className="productionOrderDetails-main">
            <div className="productionOrderDetails-id">
                <small>N.</small>
                <h6>{productionOrder.id}</h6>
            </div>
            <div className="productionOrderDetails-formulation">
                <small>Formulação:</small>
                <h6>{productionOrder.formulation.description}</h6>
            </div>
            <div className="productionOrderDetails-period">
                <small>Período de produção:</small>
                <h6 >{`${productionOrder.startDate}`}</h6>
                <h6 >{`${productionOrder.endDate}`}</h6>
            </div>
            <div className="productionOrderDetails-produced">
                <small>Produzido:</small>
                <h6>{`${productionOrder.totalProduced} ${productionOrder.formulation.product.unity.id}`}</h6>
            </div>
            <div className="productionOrderDetails-directCost">
                <small>Custo Direto:</small>
                <h6>{`${productionOrder.totalDirectCost}`}</h6>
            </div>
            <div className="productionOrderDetails-indirectCost">
                <small>Custo Indireto:</small>
                <h6>{`${productionOrder.totalIndirectCost}`}</h6>
            </div>
            <div className="productionOrderDetails-averageCost">
                <small>Custo:</small>
                <h6>{`${productionOrder.unitCost}`}</h6>
            </div>
            <div className="productionOrderDetails-actions">
                <button
                    className="btn btn-outline-secondary productionOrderDetails-btn-details"
                >
                    DETALHAR
                </button>
            </div>
        </div>
    );
}
export default CostCalculationProductionOrderDetails;