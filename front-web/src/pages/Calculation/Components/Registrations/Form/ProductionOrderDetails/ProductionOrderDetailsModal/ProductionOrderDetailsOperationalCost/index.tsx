import './styles.scss';
import { ProductionOrderOperationalCost } from 'core/types/ProductionOrder';
type Props = {
    productionOrderOperationalCost: ProductionOrderOperationalCost;
}

const ProductionOrderDetailsOperationalCost = ({ productionOrderOperationalCost }: Props) => {
    return (
        <div className="productionOrderDetailsOperationalCost-main">
            <div>
                {productionOrderOperationalCost.operationalCostType.name}
            </div>
        </div>
    )
}

export default ProductionOrderDetailsOperationalCost;