import './styles.scss';
import { ProductionOrderCostLabor } from 'core/types/ProductionOrder';
type Props = {
    productionOrderCostLabor: ProductionOrderCostLabor;
}

const ProductionOrderDetailsCostLabor = ({ productionOrderCostLabor }: Props) => {
    return (
        <div className="productionOrderDetailsOperationalCost-main">
            <div>
                {productionOrderCostLabor.sector.name}
            </div>
        </div>
    )
}

export default ProductionOrderDetailsCostLabor;