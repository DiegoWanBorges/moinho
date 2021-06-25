import './styles.scss';
import { ProductionOrderItem } from 'core/types/ProductionOrder';
type Props = {
    productionOrderItem: ProductionOrderItem;
}

const ProductionOrderDetailsInputs = ({ productionOrderItem }: Props) => {
    return (
        <div className="productionOrderDetailsInputs-main">
            <div className="productionOrderDetailsInputs-product">
                {productionOrderItem.product.name}
            </div>
            
            <div className="productionOrderDetailsInputs-quantity">
                {`${productionOrderItem.quantity} ${productionOrderItem.product.unity.id}`}
            </div>
            <div className="productionOrderDetailsInputs-cost">
                {`R$: ${productionOrderItem.cost}`}
            </div>
        </div>
    )
}

export default ProductionOrderDetailsInputs;