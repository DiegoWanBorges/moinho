import './styles.scss';
import { ProductionOrderCostLabor } from 'core/types/ProductionOrder';
import { formatPrice } from 'core/utils/utils';
type Props = {
    productionOrderCostLabor: ProductionOrderCostLabor;
}

const ProductionOrderDetailsCostLabor = ({ productionOrderCostLabor }: Props) => {
    return (
        <div className="productionOrderDetailsCostLabor-main">
            <div className="productionOrderDetailsCostLabor-name">
                {productionOrderCostLabor.sector.name}
            </div>
            <div className="productionOrderDetailsCostLabor-value">
                {`R$: ${formatPrice(productionOrderCostLabor.value)}`}
            </div>
        </div>
    )
}

export default ProductionOrderDetailsCostLabor;