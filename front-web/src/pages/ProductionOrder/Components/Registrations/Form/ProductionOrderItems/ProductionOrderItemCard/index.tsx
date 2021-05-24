import { ProductionOrderItem } from 'core/types/ProductionOrder';
import ProductionOrderItemEditModal from '../ProductionOrderItemsEditModal';

import './styles.scss';

type Props = {
    productionOrderItem: ProductionOrderItem;
    onEditItem: (productionOrderItem: ProductionOrderItem) => void;
}

function ProductionOrderItemCard({ productionOrderItem,onEditItem }: Props) {
    return (
        <div className="productionOrder-item-card-main">
            <div className="productionOrder-item-card-inf">
                <h6 className="productionOrder-item-card-inf-type">{productionOrderItem.type}</h6>
                
                <div className="productionOrder-item-card-inf-name">
                    <h6 >{productionOrderItem.product.name}</h6>
                    <small>Serie: {productionOrderItem.serie} - </small>
                    <small>Ocorrencia: {productionOrderItem.occurrence.name}</small>
                </div>

                <h6 className="productionOrder-item-card-inf-quantity">{productionOrderItem.quantity} {productionOrderItem.product.unity.id} </h6>
                
                <div className="productionOrder-item-card-actions">

                </div>
                <ProductionOrderItemEditModal 
                    productionOrderItem={productionOrderItem}
                    onEditItem={onEditItem}
                />
            </div>
        </div>
    );
}
export default ProductionOrderItemCard;
