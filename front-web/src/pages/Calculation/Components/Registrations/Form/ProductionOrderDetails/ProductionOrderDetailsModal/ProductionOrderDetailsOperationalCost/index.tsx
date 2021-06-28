import './styles.scss';
import { ProductionOrderOperationalCost } from 'core/types/ProductionOrder';
import { formatPrice } from 'core/utils/utils';
type Props = {
    productionOrderOperationalCost: ProductionOrderOperationalCost;
}

const ProductionOrderDetailsOperationalCost = ({ productionOrderOperationalCost }: Props) => {
    return (
        <div className="productionOrderDetailsOperationalCost-main">
            <div className="productionOrderDetailsOperationalCost-name">
                {productionOrderOperationalCost.operationalCostType.name}
            </div>
            <div>
                {`R$: ${formatPrice(productionOrderOperationalCost.value)}`}
            </div>
        </div>
    )
}
export default ProductionOrderDetailsOperationalCost;