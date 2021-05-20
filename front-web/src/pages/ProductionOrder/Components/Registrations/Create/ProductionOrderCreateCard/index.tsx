import { ProductionOrderItem } from 'core/types/ProductionOrder';
import './styles.scss';

type Props = {
    productionOrderItem: ProductionOrderItem;
}

const ProductionOrderCreateCard = ({ productionOrderItem }: Props) => {

    return (
        <div className="production-item-card-main">
            <div className="production-item-card-info">
                <h5 className="production-item-name">{productionOrderItem.product.id}-{productionOrderItem.product.name}</h5>
                <h5 className="production-item-quantity">{productionOrderItem.quantity} {productionOrderItem.product.unity.id}</h5>
                <h5 className="production-item-type">{productionOrderItem.type}</h5>
                
            </div>
        </div>
    )

}

export default ProductionOrderCreateCard